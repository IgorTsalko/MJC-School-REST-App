package com.epam.esm.repository.impl;

import com.epam.esm.common.exception.ErrorDefinition;
import com.epam.esm.common.entity.Tag;
import com.epam.esm.common.exception.EntityNotFoundException;
import com.epam.esm.repository.TagRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class TagRepositoryImpl implements TagRepository {

    private static final String JPQL_SELECT_ALL = "from Tag t order by t.id";
    private static final String JPQL_SELECT_BY_NAME = "from Tag where title in (:titles)";
    private static final String JPQL_SELECT_CERTIFICATE_TAGS = "select c.tags from Certificate c where c.id=:id";

    private static final String SQL_FIND_MOST_USED_TAG_FOR_USER_WITH_HIGHEST_COST_OF_ALL_ORDERS =
            "select t.id, t.title " +
                    "from \"user\" u " +
                    "         join \"order\" o on u.id = o.user_id " +
                    "         join gift_certificate gc on gc.id = o.certificate_id " +
                    "         left join gift_certificate_tag gct on gc.id = gct.gift_certificate_id " +
                    "         left join tag t on t.id = gct.tag_id " +
                    "where u.id = (select user_id from \"order\" group by user_id order by sum(price) desc limit 1) " +
                    "group by t.id " +
                    "order by count(*) desc " +
                    "limit 1";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Tag> retrieveTags(int page, int limit) {
        return entityManager.createQuery(JPQL_SELECT_ALL, Tag.class)
                .setFirstResult((page - 1) * limit)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public Tag findById(Long id) {
        Tag tag = entityManager.find(Tag.class, id);
        if (tag == null) {
            throw new EntityNotFoundException(ErrorDefinition.TAG_NOT_FOUND, id);
        }
        return tag;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Tag> retrieveCertificateTags(Long certificateId) {
        return entityManager.createQuery(JPQL_SELECT_CERTIFICATE_TAGS)
                .setParameter("id", certificateId)
                .getResultList();
    }

    @Override
    public Tag save(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    private List<Tag> findByName(List<Tag> tags) {
        List<String> names = tags.stream().map(Tag::getTitle).collect(Collectors.toList());
        return entityManager.createQuery(JPQL_SELECT_BY_NAME, Tag.class)
                .setParameter("titles", names)
                .getResultList();
    }

    @Override
    public List<Tag> saveNonExistent(List<Tag> tags) {
        List<Tag> existingTags = findByName(tags);
        List<Tag> nonexistentTags = tags.stream()
                .filter(exist -> existingTags
                        .stream()
                        .noneMatch(t -> t.getTitle().equals(exist.getTitle())))
                .collect(Collectors.toList());

        nonexistentTags.stream().distinct().collect(Collectors.toList())
                .forEach(t -> entityManager.persist(t));

        return findByName(tags);
    }

    @Override
    public void delete(Long id) {
        Tag tag = entityManager.find(Tag.class, id);
        if (tag == null) {
            throw new EntityNotFoundException(ErrorDefinition.TAG_NOT_FOUND, id);
        }
        entityManager.remove(tag);
    }

    @Override
    public Tag findMostUsedTagForUserWithHighestCostOfAllOrders() {
        return (Tag) entityManager.createNativeQuery(
                SQL_FIND_MOST_USED_TAG_FOR_USER_WITH_HIGHEST_COST_OF_ALL_ORDERS, Tag.class)
                .getSingleResult();
    }
}
