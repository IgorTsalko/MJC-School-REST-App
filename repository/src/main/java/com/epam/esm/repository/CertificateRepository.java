package com.epam.esm.repository;

import com.epam.esm.common.CertificateDTO;
import com.epam.esm.common.TagDTO;
import com.epam.esm.common.exception.EntityNotFoundException;
import com.epam.esm.common.exception.UpdateException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class CertificateRepository {

    private static final String RETRIEVE_ALL_CERTIFICATES = "SELECT * FROM gift_certificate";
    private static final String RETRIEVE_CERTIFICATE_BY_ID = "SELECT * FROM gift_certificate WHERE id=?";
    private static final String RETRIEVE_ALL_TAGS =
            "SELECT gift_certificate_id, tag_id, tag.name FROM gift_certificate_tag gct JOIN tag ON gct.tag_id = tag.id";
    private static final String SAVE_NEW_CERTIFICATE =
            "INSERT INTO gift_certificate(name, description, price, duration, create_date, last_update_date) " +
            "VALUES(:name, :description, :price, :duration, :create_date, :last_update_date)";
    private static final String UPDATE_CERTIFICATE = "UPDATE gift_certificate SET";
    private static final String DELETE_CERTIFICATE = "DELETE FROM gift_certificate WHERE id=?";
    private static final String DELETE_CERTIFICATE_TAG_CONNECTIONS =
            "DELETE FROM gift_certificate_tag WHERE gift_certificate_id=?";
    private static final String ADD_CERTIFICATE_TAG_CONNECTIONS = "INSERT INTO gift_certificate_tag VALUES(?, ?)";

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CertificateRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<CertificateDTO> getAllCertificates() {
        List<CertificateDTO> certificates =
                jdbcTemplate.query(RETRIEVE_ALL_CERTIFICATES, BeanPropertyRowMapper.newInstance(CertificateDTO.class));

        Map<Integer, List<TagDTO>> certificateTagList = new HashMap<>();
        for (CertificateDTO certificate : certificates) {
            certificateTagList.put(certificate.getId(), new ArrayList<>());
        }

        jdbcTemplate.query(RETRIEVE_ALL_TAGS, rs -> {
            do {
                int certificateId = rs.getInt(1);
                TagDTO tagDTO = new TagDTO().setId(rs.getInt(2)).setName(rs.getString(3));
                certificateTagList.get(certificateId).add(tagDTO);
            } while (rs.next());
        });

        certificates.forEach(certificate -> certificate.setTags(certificateTagList.get(certificate.getId())));

        return certificates;
    }

    public CertificateDTO getCertificate(int id) {
        return jdbcTemplate.query(RETRIEVE_CERTIFICATE_BY_ID, BeanPropertyRowMapper.newInstance(CertificateDTO.class), id)
                .stream().findAny().orElseThrow(() -> new EntityNotFoundException(id));
    }

    public CertificateDTO saveNewCertificate(CertificateDTO certificate) {
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

        return certificate.setId((Integer) keyHolder.getKeys().get("id"));
    }

    public CertificateDTO updateCertificate(int certificateId, CertificateDTO certificate) {
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
        certificate.setLastUpdateDate(now);
        params.addValue("now", now).addValue("id", certificateId);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        if (namedParameterJdbcTemplate.update(updateCertificateSql.toString(), params, keyHolder) == 0) {
            throw new EntityNotFoundException(certificateId);
        }

        return certificate
                .setId(certificateId)
                .setName((String) keyHolder.getKeys().get("name"))
                .setDescription((String) keyHolder.getKeys().get("description"))
                .setPrice((BigDecimal) keyHolder.getKeys().get("price"))
                .setDuration((Integer) keyHolder.getKeys().get("duration"))
                .setCreateDate(((Timestamp) keyHolder.getKeys().get("create_date")).toLocalDateTime());
    }

    public void deleteCertificate(int id) {
        if (jdbcTemplate.update(DELETE_CERTIFICATE, id) == 0) {
            throw new EntityNotFoundException(id);
        }
    }

    public void addCertificateTagConnections(int certificateId, List<TagDTO> tags) {
        jdbcTemplate.batchUpdate(ADD_CERTIFICATE_TAG_CONNECTIONS, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                TagDTO tag = tags.get(i);
                ps.setInt(1, certificateId);
                ps.setInt(2, tag.getId());
            }

            @Override
            public int getBatchSize() {
                return tags.size();
            }
        });
    }

    public void deleteCertificateTagConnections(int certificateId) {
        if (jdbcTemplate.update(DELETE_CERTIFICATE_TAG_CONNECTIONS, certificateId) == 0) {
            throw new UpdateException(certificateId);
        }
    }
}
