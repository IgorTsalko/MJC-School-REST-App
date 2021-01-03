package com.epam.esm.repository;

import com.epam.esm.object.CertificateDTO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class CertificatesRepository {

    private final JdbcTemplate jdbcTemplate;

    public CertificatesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String RETRIEVE_ALL_CERTIFICATES = "SELECT * FROM gift_certificate";
    private static final String RETRIEVE_CERTIFICATE_BY_ID = "SELECT * FROM gift_certificate WHERE id=?";
    private static final String SAVE_NEW_CERTIFICATE
            = "INSERT INTO gift_certificate(name, description, price, duration) VALUES(?, ?, ?, ?)";
    private static final String UPDATE_CERTIFICATE
            = "UPDATE gift_certificate SET name=?, description=?, price=?, duration=?, last_update_date=? WHERE id=?";
    private static final String DELETE_CERTIFICATE = "DELETE FROM gift_certificate WHERE id=?";

    public List<CertificateDTO> getAllCertificates() {
        return jdbcTemplate.query(RETRIEVE_ALL_CERTIFICATES, new BeanPropertyRowMapper<>(CertificateDTO.class));
    }

    public CertificateDTO getCertificateById(int id) {
        return jdbcTemplate
                .query(RETRIEVE_CERTIFICATE_BY_ID, new BeanPropertyRowMapper<>(CertificateDTO.class), id)
                .stream().findAny().orElse(null);
    }

    public void saveNewCertificate(CertificateDTO certificate) {
        jdbcTemplate.update(SAVE_NEW_CERTIFICATE,
                certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getDuration());
    }

    public void updateCertificateById(int id, CertificateDTO certificate) {
        jdbcTemplate.update(UPDATE_CERTIFICATE,
                certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getDuration(),
                LocalDateTime.now(),
                id);
    }

    public void deleteCertificateById(int id) {
        jdbcTemplate.update(DELETE_CERTIFICATE, id);
    }
}
