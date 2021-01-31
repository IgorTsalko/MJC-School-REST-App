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
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class CertificateRepositoryImpl implements CertificateRepository {

    private static final String JPQL_SELECT_TAGS_BY_NAME = "from Tag t where t.title in (:titles)";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Certificate> getCertificates(CertificateSearchParams params, int page, int limit) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Certificate> criteriaQuery = cb.createQuery(Certificate.class);
        Root<Certificate> certificates = criteriaQuery.from(Certificate.class);
        certificates.fetch("tags", JoinType.LEFT);

        List<Long> filteredCertificateIds = getFilteredCertificateIds(params, page, limit);
        criteriaQuery.distinct(true).where(certificates.get("id").in(filteredCertificateIds));
        setSorting(params, cb, criteriaQuery, certificates);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    private List<Long> getFilteredCertificateIds(CertificateSearchParams params, int page, int limit) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Certificate> criteriaQuery = cb.createQuery(Certificate.class);
        Root<Certificate> certificates = criteriaQuery.from(Certificate.class);
        ArrayList<Predicate> predicates = new ArrayList<>();

        if (params.getTitle() != null) {
            predicates.add(cb.like(cb.lower(
                    certificates.get("title")),
                    params.getTitle().toLowerCase() + "%"));
        }
        if (params.getDescription() != null) {
            predicates.add(cb.like(cb.lower(
                    certificates.get("description")),
                    params.getDescription().toLowerCase() + "%"));
        }

        if (params.getTags() != null) {
            List<Tag> tags = entityManager
                    .createQuery(JPQL_SELECT_TAGS_BY_NAME, Tag.class)
                    .setParameter("titles", params.getTags())
                    .getResultList();
            if (tags.size() != params.getTags().size()) {
                return new ArrayList<>();
            }
            tags.forEach(t -> predicates.add(cb.isMember(t, certificates.get("tags"))));
        }

        Predicate[] allPredicates = new Predicate[predicates.size()];
        predicates.toArray(allPredicates);
        criteriaQuery = criteriaQuery.distinct(true).where(allPredicates);
        setSorting(params, cb, criteriaQuery, certificates);

        return entityManager
                .createQuery(criteriaQuery)
                .setFirstResult((page - 1) * limit)
                .setMaxResults(limit)
                .getResultList()
                .stream()
                .map(Certificate::getId)
                .collect(Collectors.toList());
    }

    private void setSorting(CertificateSearchParams params,
                            CriteriaBuilder cb,
                            CriteriaQuery<Certificate> criteriaQuery,
                            Root<Certificate> certificates) {
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
        entityManager.persist(certificate);
        return certificate;
    }

    @Override
    public Certificate put(Long id, Certificate certificate) {
        Certificate cert = entityManager.find(Certificate.class, id);
        if (cert == null) {
            throw new EntityNotFoundException(ErrorDefinition.CERTIFICATE_NOT_FOUND, id);
        }
        certificate.setId(id);
        certificate.setCreateDate(cert.getCreateDate());

        return entityManager.merge(certificate);
    }

    @Override
    public Certificate update(Long id, Certificate certificate) {
        Certificate updatedCert = entityManager.find(Certificate.class, id);

        if (updatedCert == null) {
            throw new EntityNotFoundException(ErrorDefinition.CERTIFICATE_NOT_FOUND, id);
        }

        String name = certificate.getTitle();
        String description = certificate.getDescription();
        BigDecimal price = certificate.getPrice();
        Integer duration = certificate.getDuration();
        List<Tag> tags = certificate.getTags();

        if (name != null) {
            updatedCert.setTitle(name);
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
