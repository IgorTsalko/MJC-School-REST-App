package com.epam.esm.repository.impl;

import com.epam.esm.common.entity.Certificate;
import com.epam.esm.common.entity.CertificateSearchParams;
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

    private static final String JPQL_SELECT_TAGS_BY_NAME = "from Tag t where t.name in (:names)";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Certificate> getAll(CertificateSearchParams params) {
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

        Predicate[] allPredicates = new Predicate[predicates.size()];
        predicates.toArray(allPredicates);
        criteriaQuery = criteriaQuery.distinct(true).where(allPredicates);
        criteriaQuery = criteriaQuery.where(allPredicates);

        if (params.getTags() != null) {
            List<Tag> tags = entityManager
                    .createQuery(JPQL_SELECT_TAGS_BY_NAME, Tag.class)
                    .setParameter("names", params.getTags())
                    .getResultList();

            List<Predicate> tagPredicates = new ArrayList<>();
            tags.forEach(t -> tagPredicates.add(cb.isMember(t, certificates.get("tags"))));
            Predicate[] tagArrayPredicates = new Predicate[tagPredicates.size()];
            tagPredicates.toArray(tagArrayPredicates);

            criteriaQuery = criteriaQuery.select(certificates).where(cb.and(tagArrayPredicates));
        }

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

    @Override
    public Certificate get(Long id) {
        Certificate certificate = entityManager.find(Certificate.class, id);
        if (certificate == null) {
            throw new EntityNotFoundException(ErrorDefinition.CERTIFICATE_NOT_FOUND, id);
        }
        return certificate;
    }

    @Override
    public Certificate create(Certificate certificate) {
        certificate.setCreateDate(LocalDateTime.now());
        certificate.setLastUpdateDate(LocalDateTime.now());
        entityManager.persist(certificate);
        return certificate;
    }

    @Override
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

    @Override
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

    @Override
    public void delete(Long id) {
        Certificate certificate = entityManager.find(Certificate.class, id);
        if (certificate == null) {
            throw new EntityNotFoundException(ErrorDefinition.CERTIFICATE_NOT_FOUND, id);
        }
        entityManager.remove(certificate);
    }
}
