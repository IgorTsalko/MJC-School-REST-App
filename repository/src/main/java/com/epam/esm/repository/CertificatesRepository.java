package com.epam.esm.repository;

import com.epam.esm.common.CertificateDTO;
import com.epam.esm.common.exception.EntityNotFoundException;
import com.epam.esm.common.exception.UpdateException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    private static final String SAVE_NEW_CERTIFICATE = "INSERT INTO gift_certificate(name, description, price, duration) " +
            "VALUES(:name, :description, :price, :duration)";
    private static final String UPDATE_CERTIFICATE = "UPDATE gift_certificate " +
            "SET name=:name, description=:description, price=:price, duration=:duration, last_update_date=now() WHERE id=:id";
    private static final String DELETE_CERTIFICATE = "DELETE FROM gift_certificate WHERE id=?";

    public List<CertificateDTO> getAllCertificates() {
        return jdbcTemplate.query(RETRIEVE_ALL_CERTIFICATES, new BeanPropertyRowMapper<>(CertificateDTO.class));
    }

    public CertificateDTO getCertificateById(int id) {
        return jdbcTemplate.query(RETRIEVE_CERTIFICATE_BY_ID, new BeanPropertyRowMapper<>(CertificateDTO.class), id)
                .stream().findAny().orElseThrow(() -> new EntityNotFoundException(id));
    }

    public CertificateDTO saveNewCertificate(CertificateDTO certificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", certificate.getName())
                .addValue("description", certificate.getDescription())
                .addValue("price", certificate.getPrice())
                .addValue("duration", certificate.getDuration());

        if (namedParameterJdbcTemplate.update(SAVE_NEW_CERTIFICATE, params, keyHolder, new String[]{"id"}) == 0) {
            throw new UpdateException(certificate.getName());
        } else {
            return getCertificateById(keyHolder.getKey().intValue());
        }
    }

    public CertificateDTO updateCertificateById(CertificateDTO certificate) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", certificate.getName())
                .addValue("description", certificate.getDescription())
                .addValue("price", certificate.getPrice())
                .addValue("duration", certificate.getDuration())
                .addValue("id", certificate.getId());
        if (namedParameterJdbcTemplate.update(UPDATE_CERTIFICATE, params) == 0) {
            throw new EntityNotFoundException(certificate.getId());
        } else {
            return getCertificateById(certificate.getId());
        }
    }

    public void deleteCertificateById(int id) {
        if (jdbcTemplate.update(DELETE_CERTIFICATE, id) < 1) {
            throw new EntityNotFoundException(id);
        }
    }
}
