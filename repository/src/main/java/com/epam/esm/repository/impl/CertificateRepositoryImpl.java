package com.epam.esm.repository.impl;

import com.epam.esm.common.entity.Certificate;
import com.epam.esm.common.entity.CertificateSearchParams;
import com.epam.esm.common.exception.ErrorDefinition;
import com.epam.esm.common.entity.Tag;
import com.epam.esm.common.exception.EntityNotFoundException;
import com.epam.esm.common.sorting.CertificateSorting;
import com.epam.esm.common.sorting.SortOrder;
import com.epam.esm.repository.CertificateRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class CertificateRepositoryImpl implements CertificateRepository {

    private static final String JPQL_SELECT_CERTIFICATE_BY_ID
            = "select distinct c from Certificate c left join fetch c.tags where c.id=:id";
    private static final String JPQL_SELECT_TAGS_BY_NAME = "from Tag t where t.title in (:titles)";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Certificate> retrieveCertificates(CertificateSearchParams params, int page, int limit) {
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

        if (params.getTagTitles() != null) {
            List<Tag> tags = entityManager
                    .createQuery(JPQL_SELECT_TAGS_BY_NAME, Tag.class)
                    .setParameter("titles", params.getTagTitles())
                    .getResultList();
            if (tags.size() != params.getTagTitles().size()) {
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
        if (!CollectionUtils.isEmpty(params.getSorting())) {
            List<Order> orderBy = new ArrayList<>();
            for (CertificateSorting sorting : params.getSorting()) {
                if (sorting.getSortOrder().equals(SortOrder.DESC)) {
                    orderBy.add(cb.desc(certificates.get(sorting.getColumn().getColumnTitle())));
                } else {
                    orderBy.add(cb.asc(certificates.get(sorting.getColumn().getColumnTitle())));
                }
            }
            criteriaQuery.orderBy(orderBy);
        } else {
            criteriaQuery.orderBy(cb.asc(certificates.get("id")));
        }
    }

    @Override
    public Certificate findById(Long id) {
        return entityManager.createQuery(JPQL_SELECT_CERTIFICATE_BY_ID, Certificate.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(ErrorDefinition.CERTIFICATE_NOT_FOUND, id));
    }

    @Override
    public Certificate save(Certificate certificate) {
        entityManager.persist(certificate);
        return certificate;
    }

    @Override
    public Certificate replace(Long id, Certificate certificate) {
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
        Certificate updatedCert = entityManager.createQuery(JPQL_SELECT_CERTIFICATE_BY_ID, Certificate.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(ErrorDefinition.CERTIFICATE_NOT_FOUND, id));

        String title = certificate.getTitle();
        String description = certificate.getDescription();
        BigDecimal price = certificate.getPrice();
        Integer duration = certificate.getDuration();

        if (title != null) {
            updatedCert.setTitle(title);
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
