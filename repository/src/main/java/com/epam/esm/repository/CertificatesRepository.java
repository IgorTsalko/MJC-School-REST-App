package com.epam.esm.repository;

import com.epam.esm.common.CertificateDTO;
import com.epam.esm.common.Tag;
import com.epam.esm.common.exception.EntityNotFoundException;
import com.epam.esm.common.exception.UpdateException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CertificatesRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CertificatesRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private static final String RETRIEVE_ALL_CERTIFICATES = "SELECT * FROM gift_certificate";
    private static final String RETRIEVE_CERTIFICATE_BY_ID = "SELECT * FROM gift_certificate WHERE id=?";
    private static final String RETRIEVE_TAGS_BY_CERTIFICATE_ID = "SELECT id, name FROM gift_certificate_tag gct " +
            "JOIN tag ON gct.tag_id = tag.id WHERE gct.gift_certificate_id=?";
    private static final String RETRIEVE_ALL_TAGS =
            "SELECT gift_certificate_id, tag_id, tag.name FROM gift_certificate_tag gct JOIN tag ON gct.tag_id = tag.id";
    private static final String SAVE_NEW_CERTIFICATE = "INSERT INTO gift_certificate(name, description, price, duration) " +
            "VALUES(:name, :description, :price, :duration)";
    private static final String UPDATE_CERTIFICATE = "UPDATE gift_certificate " +
            "SET name=:name, description=:description, price=:price, duration=:duration, last_update_date=now() WHERE id=:id";
    private static final String DELETE_CERTIFICATE = "DELETE FROM gift_certificate WHERE id=?";
    private static final String DELETE_CERTIFICATE_TAG_RELATIONS =
            "DELETE FROM gift_certificate_tag WHERE gift_certificate_id=?";

    private static final String RETRIEVE_TAG_TEMPLATE = "SELECT * FROM tag WHERE ";

    public List<CertificateDTO> getAllCertificates() {
        List<CertificateDTO> certificates =
                jdbcTemplate.query(RETRIEVE_ALL_CERTIFICATES, BeanPropertyRowMapper.newInstance(CertificateDTO.class));

        Map<Integer, CertificateDTO> idCertificateMap = new HashMap<>();
        for (CertificateDTO certificateDTO : certificates) {
            idCertificateMap.put(certificateDTO.getId(), certificateDTO);
        }

        jdbcTemplate.query(RETRIEVE_ALL_TAGS, rs -> {
            while (rs.next()) {
                int certificateId = rs.getInt(1);
                Tag tag = new Tag().setId(rs.getInt(2)).setName(rs.getString(3));
                idCertificateMap.get(certificateId).getTags().add(tag);
            }
        });

        return certificates;
    }

    public CertificateDTO getCertificateById(int id) {
        return jdbcTemplate.query(RETRIEVE_CERTIFICATE_BY_ID, BeanPropertyRowMapper.newInstance(CertificateDTO.class), id)
                .stream().findAny().orElseThrow(() -> new EntityNotFoundException(id))
                .setTags(jdbcTemplate.query(RETRIEVE_TAGS_BY_CERTIFICATE_ID, BeanPropertyRowMapper.newInstance(Tag.class), id));
    }

    public CertificateDTO saveNewCertificate(CertificateDTO certificate) {
        
        // DONE! retrieve all tags that exist among sent tags.
        StringBuilder retrieveTagSql = new StringBuilder(RETRIEVE_TAG_TEMPLATE);
        certificate.getTags().forEach(tag -> retrieveTagSql.append("name='").append(tag.getName()).append("' OR "));
        retrieveTagSql.delete(retrieveTagSql.lastIndexOf(" OR "), retrieveTagSql.length());
        List<Tag> tags = jdbcTemplate.query(retrieveTagSql.toString(), BeanPropertyRowMapper.newInstance(Tag.class));

        // todo: if some tag don't exist, have to create it
        for (Tag tag : certificate.getTags()) {
            // todo: dynamic query creation
            if (!tags.contains(tag)) {
                // todo: have to add tag value into INSERT sql
            }
        }
        // todo: insert all missing tags

        // todo: again retrieve all sent tags in order to get their id

        // todo: delete all relations certificate and tags

        // todo: add new relations

        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", certificate.getName())
                .addValue("description", certificate.getDescription())
                .addValue("price", certificate.getPrice())
                .addValue("duration", certificate.getDuration());

        if (namedParameterJdbcTemplate.update(SAVE_NEW_CERTIFICATE, params, keyHolder) == 0) {
            throw new UpdateException(certificate.getName());
        }

        return certificate.setId((Integer) keyHolder.getKeys().get("id"))
                .setCreateDate(((Timestamp) keyHolder.getKeys().get("create_date")).toLocalDateTime())
                .setLastUpdateDate(((Timestamp) keyHolder.getKeys().get("last_update_date")).toLocalDateTime());
    }

    public CertificateDTO updateCertificateById(CertificateDTO certificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", certificate.getName())
                .addValue("description", certificate.getDescription())
                .addValue("price", certificate.getPrice())
                .addValue("duration", certificate.getDuration())
                .addValue("id", certificate.getId());

        if (namedParameterJdbcTemplate.update(UPDATE_CERTIFICATE, params, keyHolder) == 0) {
            throw new EntityNotFoundException(certificate.getId());
        }

        return certificate.setCreateDate(((Timestamp) keyHolder.getKeys().get("create_date")).toLocalDateTime())
                .setLastUpdateDate(((Timestamp) keyHolder.getKeys().get("last_update_date")).toLocalDateTime());
    }

    public void deleteCertificateById(int id) {
        if (jdbcTemplate.update(DELETE_CERTIFICATE, id) < 1) {
            throw new EntityNotFoundException(id);
        }
    }
}
