package com.epam.esm.repository;

import com.epam.esm.common.entity.GiftCertificate;
import com.epam.esm.common.entity.GiftCertificateParams;
import com.epam.esm.common.entity.Tag;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class GiftCertificateSpecification implements Specification<GiftCertificate> {

    private final GiftCertificateParams params;
    private List<Tag> tags;

    public GiftCertificateSpecification(GiftCertificateParams params) {
        this.params = params;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public Predicate toPredicate(Root<GiftCertificate> root,
                                 CriteriaQuery<?> query,
                                 CriteriaBuilder criteriaBuilder) {
        query.distinct(true);
        List<Predicate> predicates = new ArrayList<>();

        if (params.getTitle() != null) {
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("title")),
                    params.getTitle().toLowerCase() + "%")
            );
        }
        if (params.getDescription() != null) {
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("description")),
                    params.getDescription().toLowerCase() + "%")
            );
        }
        if (tags != null) {
            tags.forEach(t -> predicates.add(criteriaBuilder.isMember(t, root.get("tags"))));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
