package com.epam.esm.repository.impl;

import com.epam.esm.common.entity.GiftCertificate;
import com.epam.esm.common.entity.GiftCertificateParams;
import com.epam.esm.common.entity.Tag;
import com.epam.esm.common.sorting.Sorting;
import com.epam.esm.common.sorting.SortOrder;
import com.epam.esm.repository.GiftCertificateRepositoryOld;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class GiftCertificateRepositoryOldImpl implements GiftCertificateRepositoryOld {

    private static final String JPQL_SELECT_TAGS_BY_NAME = "from Tag t where t.title in (:titles)";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<GiftCertificate> getGiftCertificates(GiftCertificateParams params, int page, int limit) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = cb.createQuery(GiftCertificate.class);
        Root<GiftCertificate> certificates = criteriaQuery.from(GiftCertificate.class);
        certificates.fetch("tags", JoinType.LEFT);

        List<Long> filteredCertificateIds = getFilteredCertificateIds(params, page, limit);
        criteriaQuery.distinct(true).where(certificates.get("id").in(filteredCertificateIds));
        setSorting(params, cb, criteriaQuery, certificates);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    private List<Long> getFilteredCertificateIds(GiftCertificateParams params, int page, int limit) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = cb.createQuery(GiftCertificate.class);
        Root<GiftCertificate> certificates = criteriaQuery.from(GiftCertificate.class);
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
                .map(GiftCertificate::getId)
                .collect(Collectors.toList());
    }

    private void setSorting(GiftCertificateParams params,
                            CriteriaBuilder cb,
                            CriteriaQuery<GiftCertificate> criteriaQuery,
                            Root<GiftCertificate> certificates) {
        if (!CollectionUtils.isEmpty(params.getSorts())) {
            List<Order> orderBy = new ArrayList<>();
            for (Sorting sorting : params.getSorts()) {
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
}
