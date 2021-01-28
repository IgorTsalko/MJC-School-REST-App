package com.epam.esm.repository.impl;

import com.epam.esm.common.entity.Certificate;
import com.epam.esm.common.SearchParams;
import com.epam.esm.common.ErrorDefinition;
import com.epam.esm.common.entity.Tag;
import com.epam.esm.common.exception.EntityNotFoundException;
import com.epam.esm.repository.CertificateRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class CertificateRepositoryImpl implements CertificateRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Certificate> getAll(SearchParams params) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Certificate> criteriaQuery = cb.createQuery(Certificate.class);
        Root<Certificate> certificates = criteriaQuery.from(Certificate.class);
        ArrayList<Predicate> predicates = new ArrayList<>();

        if (params.getName() != null) {
            predicates.add(cb.like(cb.lower(
                    certificates.get("name")),
                    params.getName().toLowerCase() + "%"));
        }
        if (params.getDescription() != null) {
            predicates.add(cb.like(cb.lower(
                    certificates.get("description")),
                    params.getDescription().toLowerCase() + "%"));
        }
        if (params.getTag() != null) {
            Join<Certificate, Tag> tags = certificates.join("tags", JoinType.LEFT);
            predicates.add(cb.like(tags.get("name"), params.getTag()));
        }

        Predicate[] allPredicates = new Predicate[predicates.size()];
        predicates.toArray(allPredicates);
        criteriaQuery = criteriaQuery.distinct(true).where(allPredicates);

        if (params.getSort() != null) {
            if (params.getSortOrder() != null) {
                if (params.getSortOrder().name().equals("DESC")) {
                    criteriaQuery.orderBy(cb.desc(certificates.get(params.getSort())));
                } else if (params.getSortOrder().name().equals("ASC")) {
                    criteriaQuery.orderBy(cb.asc(certificates.get(params.getSort())));
                }
            } else {
                criteriaQuery.orderBy(cb.asc(certificates.get(params.getSort())));
            }
        }

        certificates.fetch("tags", JoinType.LEFT);
        return entityManager.createQuery(criteriaQuery).getResultList();
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

        if (updatedCert == null) {
            throw new EntityNotFoundException(ErrorDefinition.CERTIFICATE_NOT_FOUND, id);
        }

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
