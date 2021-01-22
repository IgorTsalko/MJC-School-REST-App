package com.epam.esm.repository.impl;

import com.epam.esm.common.Certificate;
import com.epam.esm.common.SearchParams;
import com.epam.esm.common.ErrorDefinition;
import com.epam.esm.common.Tag;
import com.epam.esm.common.exception.EntityNotFoundException;
import com.epam.esm.repository.CertificateRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class CertificateRepositoryImpl implements CertificateRepository {

    private static final String RETRIEVE_CERTIFICATES =
            "SELECT gift_certificate_id AS id, cert.name, description, price, duration, create_date, last_update_date" +
                    " FROM gift_certificate cert JOIN gift_certificate_tag gct ON cert.id = gct.gift_certificate_id " +
                    "JOIN tag ON gct.tag_id = tag.id";
    private static final String CERTIFICATE_GROUP_BY
            = " GROUP BY gift_certificate_id, cert.name, description, price, duration, create_date, last_update_date";
    private static final String RETRIEVE_CERTIFICATE_BY_ID = "SELECT * FROM gift_certificate WHERE id=?";
    private static final String SAVE_NEW_CERTIFICATE =
            "INSERT INTO gift_certificate(name, description, price, duration, create_date, last_update_date) " +
                    "VALUES(:name, :description, :price, :duration, :create_date, :last_update_date)";
    private static final String UPSERT =
            "INSERT INTO gift_certificate(id, name, description, price, duration, create_date, last_update_date) " +
                    "VALUES(:id, :name, :description, :price, :duration, :create_date, :last_update_date) " +
                    "ON CONFLICT (id) DO UPDATE SET name=:name, description=:description, price=:price, " +
                    "duration=:duration, last_update_date=:last_update_date";
    private static final String UPDATE_CERTIFICATE = "UPDATE gift_certificate SET";
    private static final String DELETE_CERTIFICATE = "DELETE FROM gift_certificate WHERE id=?";
    private static final String DELETE_CERTIFICATE_TAG_CONNECTIONS =
            "DELETE FROM gift_certificate_tag WHERE gift_certificate_id=?";
    private static final String ADD_CERTIFICATE_TAG_CONNECTIONS = "INSERT INTO gift_certificate_tag VALUES(?, ?)";

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CertificateRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Certificate> getAll(SearchParams params) {
        StringBuilder retrieveCertificatesSql = new StringBuilder(RETRIEVE_CERTIFICATES);

        if (params.getName() != null || params.getDescription() != null || params.getTag() != null) {
            retrieveCertificatesSql.append(" WHERE ");
            List<String> conditions = new ArrayList<>();
            if (params.getName() != null) {
                conditions.add("cert.name ~* '^" + params.getName());
            }
            if (params.getDescription() != null) {
                conditions.add("description ~* '^" + params.getDescription());
            }
            if (params.getTag() != null) {
                conditions.add("tag.name='" + params.getTag());
            }
            retrieveCertificatesSql.append(String.join("' AND ", conditions)).append("'");
        }
        retrieveCertificatesSql.append(CERTIFICATE_GROUP_BY);

        if (params.getSort() != null) {
            retrieveCertificatesSql
                    .append(" ORDER BY ").append(params.getSort());
            if (params.getSort_order() != null) {
                retrieveCertificatesSql.append(" ").append(params.getSort_order());
            }
        }

        return jdbcTemplate
                .query(retrieveCertificatesSql.toString(), BeanPropertyRowMapper.newInstance(Certificate.class));
    }

    public Certificate get(Long id) {
        return jdbcTemplate.query(RETRIEVE_CERTIFICATE_BY_ID, BeanPropertyRowMapper.newInstance(Certificate.class), id)
                .stream().findAny()
                .orElseThrow(() -> new EntityNotFoundException(ErrorDefinition.CERTIFICATE_NOT_FOUND, id));
    }

    public Certificate create(Certificate certificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        certificate.setCreateDate(LocalDateTime.now());
        certificate.setLastUpdateDate(LocalDateTime.now());
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", certificate.getName())
                .addValue("description", certificate.getDescription())
                .addValue("price", certificate.getPrice())
                .addValue("duration", certificate.getDuration())
                .addValue("create_date", certificate.getCreateDate())
                .addValue("last_update_date", certificate.getLastUpdateDate());

        namedParameterJdbcTemplate.update(SAVE_NEW_CERTIFICATE, params, keyHolder);

        return certificate.setId(((Number) keyHolder.getKeys().get("id")).longValue());
    }

    public Certificate upsert(Long id, Certificate certificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        certificate.setCreateDate(LocalDateTime.now());
        certificate.setLastUpdateDate(LocalDateTime.now());
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("name", certificate.getName())
                .addValue("description", certificate.getDescription())
                .addValue("price", certificate.getPrice())
                .addValue("duration", certificate.getDuration())
                .addValue("create_date", certificate.getCreateDate())
                .addValue("last_update_date", certificate.getLastUpdateDate());

        namedParameterJdbcTemplate.update(UPSERT, params, keyHolder);

        return certificate.setId(((Number) keyHolder.getKeys().get("id")).longValue());
    }

    public Certificate update(Long id, Certificate certificate) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        StringBuilder updateCertificateSql = new StringBuilder(UPDATE_CERTIFICATE);
        if (certificate.getName() != null) {
            updateCertificateSql.append(" name=:name,");
            params.addValue("name", certificate.getName());
        }
        if (certificate.getDescription() != null) {
            updateCertificateSql.append(" description=:description,");
            params.addValue("description", certificate.getDescription());
        }
        if (certificate.getPrice() != null) {
            updateCertificateSql.append(" price=:price,");
            params.addValue("price", certificate.getPrice());
        }
        if (certificate.getDuration() != null) {
            updateCertificateSql.append(" duration=:duration,");
            params.addValue("duration", certificate.getDuration());
        }
        updateCertificateSql.append(" last_update_date=:now WHERE id=:id");
        LocalDateTime now = LocalDateTime.now();
        params.addValue("now", now).addValue("id", id);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        if (namedParameterJdbcTemplate.update(updateCertificateSql.toString(), params, keyHolder) == 0) {
            throw new EntityNotFoundException(ErrorDefinition.CERTIFICATE_NOT_FOUND, id);
        }

        return certificate
                .setId(id)
                .setName((String) keyHolder.getKeys().get("name"))
                .setDescription((String) keyHolder.getKeys().get("description"))
                .setPrice((BigDecimal) keyHolder.getKeys().get("price"))
                .setDuration((Integer) keyHolder.getKeys().get("duration"))
                .setCreateDate(((Timestamp) keyHolder.getKeys().get("create_date")).toLocalDateTime())
                .setLastUpdateDate(now);
    }

    public void delete(Long id) {
        if (jdbcTemplate.update(DELETE_CERTIFICATE, id) == 0) {
            throw new EntityNotFoundException(ErrorDefinition.CERTIFICATE_NOT_FOUND, id);
        }
    }

    public void addCertificateTagConnections(Long id, List<Tag> tags) {
        List<Object[]> params = tags.stream().map(tag -> new Object[]{id, tag.getId()})
                .collect(Collectors.toList());
        jdbcTemplate.batchUpdate(ADD_CERTIFICATE_TAG_CONNECTIONS, params);
    }

    public void deleteCertificateTagConnections(Long id) {
        jdbcTemplate.update(DELETE_CERTIFICATE_TAG_CONNECTIONS, id);
    }
}
