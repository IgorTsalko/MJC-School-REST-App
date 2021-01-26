package com.epam.esm.repository.impl;

import com.epam.esm.common.Certificate;
import com.epam.esm.common.SearchParams;
import com.epam.esm.common.ErrorDefinition;
import com.epam.esm.common.Tag;
import com.epam.esm.common.exception.EntityNotFoundException;
import com.epam.esm.repository.CertificateRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class CertificateRepositoryImpl implements CertificateRepository {

    private static final String JPQL_SELECT_ALL = "select distinct c from Certificate c left join fetch c.tags t";

    @PersistenceContext
    private EntityManager entityManager;

    public List<Certificate> getAll(SearchParams params) {
        StringBuilder jpqlQuery = new StringBuilder(JPQL_SELECT_ALL);
        if (params.getName() != null || params.getDescription() != null || params.getTag() != null) {
            jpqlQuery.append(" where ");
            List<String> conditions = new ArrayList<>();
            if (params.getName() != null) {
                conditions.add("lower(c.name) like lower('" + params.getName() + "%')");
            }
            if (params.getDescription() != null) {
                conditions.add("lower(c.description) like lower('" + params.getDescription() + "%')");
            }
            //todo: search by tag name not working yet
            if (params.getTag() != null) {
                conditions.add("'" + params.getTag() + "' member of t");
            }
            jpqlQuery.append(String.join(" and ", conditions));
        }
        if (params.getSort() != null) {
            jpqlQuery.append(" order by ").append("c.").append(params.getSort());
            if (params.getSortOrder() != null) {
                jpqlQuery.append(" ").append(params.getSortOrder());
            }
        }

        return entityManager.createQuery(jpqlQuery.toString(), Certificate.class).getResultList();
    }

    public Certificate get(Long id) {
        Certificate certificate = entityManager.find(Certificate.class, id);
        if (certificate == null) {
            throw new EntityNotFoundException(ErrorDefinition.CERTIFICATE_NOT_FOUND, id);
        }
        return certificate;
    }

    public Certificate create(Certificate certificate) {
        certificate.setCreateDate(LocalDateTime.now());
        certificate.setLastUpdateDate(LocalDateTime.now());
        entityManager.persist(certificate);
        return certificate;
    }

    public Certificate upsert(Long id, Certificate certificate) {

        Certificate cert = entityManager.find(Certificate.class, id);
        if (cert == null) {
            throw new EntityNotFoundException(ErrorDefinition.CERTIFICATE_NOT_FOUND, id);
        }
        certificate.setId(id);
        certificate.setCreateDate(cert.getCreateDate());
        certificate.setLastUpdateDate(LocalDateTime.now());

        return entityManager.merge(certificate);
    }

    public Certificate update(Long id, Certificate certificate) {
        Certificate updatedCert = entityManager.find(Certificate.class, id);

        String name = certificate.getName();
        String description = certificate.getDescription();
        BigDecimal price = certificate.getPrice();
        Integer duration = certificate.getDuration();
        List<Tag> tags = certificate.getTags();

        if (name != null) {
            updatedCert.setName(name);
        }
        if (description != null) {
            updatedCert.setDescription(description);
        }
        if (price != null) {
            updatedCert.setPrice(price);
        }
        if (duration != null) {
            updatedCert.setDuration(duration);
        }
        if (tags != null) {
            updatedCert.setTags(tags);
        }
        updatedCert.setLastUpdateDate(LocalDateTime.now());

        return updatedCert;
    }

    public void delete(Long id) {
        Certificate certificate = entityManager.find(Certificate.class, id);
        if (certificate == null) {
            throw new EntityNotFoundException(ErrorDefinition.CERTIFICATE_NOT_FOUND, id);
        }
        entityManager.remove(certificate);
    }
}
