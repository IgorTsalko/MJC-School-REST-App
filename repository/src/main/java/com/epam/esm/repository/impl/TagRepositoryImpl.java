package com.epam.esm.repository.impl;

import com.epam.esm.common.ErrorDefinition;
import com.epam.esm.common.entity.Tag;
import com.epam.esm.common.exception.EntityNotFoundException;
import com.epam.esm.repository.TagRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class TagRepositoryImpl implements TagRepository {

    private static final String JPQL_SELECT_ALL = "from Tag";
    private static final String JPQL_SELECT_BY_NAME = "from Tag where name in (:names)";
    private static final String JPQL_SELECT_CERTIFICATE_TAGS = "select c.tags from Certificate c where c.id=:id";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Tag> getAll(Integer page, Integer limit) {
        int firstResult = page == null ? 0 : (page - 1) * limit;
        return entityManager.createQuery(JPQL_SELECT_ALL, Tag.class)
                .setFirstResult(firstResult)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public Tag get(Long id) {
        Tag tag = entityManager.find(Tag.class, id);
        if (tag == null) {
            throw new EntityNotFoundException(ErrorDefinition.TAG_NOT_FOUND, id);
        }
        return tag;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Tag> getCertificateTags(Long certificateId) {
        return entityManager.createQuery(JPQL_SELECT_CERTIFICATE_TAGS)
                .setParameter("id", certificateId)
                .getResultList();
    }

    @Override
    public Tag create(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    private List<Tag> getByNames(List<Tag> tags) {
        List<String> names = tags.stream().map(Tag::getName).collect(Collectors.toList());
        return entityManager.createQuery(JPQL_SELECT_BY_NAME, Tag.class)
                .setParameter("names", names)
                .getResultList();
    }

    @Override
    public List<Tag> createNonExistent(List<Tag> tags) {
        List<Tag> existingTags = getByNames(tags);
        List<Tag> nonexistentTags = tags.stream()
                .filter(exist -> existingTags
                        .stream()
                        .noneMatch(t -> t.getName().equals(exist.getName())))
                .collect(Collectors.toList());

        nonexistentTags.stream().distinct().collect(Collectors.toList())
                .forEach(t -> entityManager.persist(t));

        return getByNames(tags);
    }

    @Override
    public void delete(Long id) {
        Tag tag = entityManager.find(Tag.class, id);
        if (tag == null) {
            throw new EntityNotFoundException(ErrorDefinition.TAG_NOT_FOUND, id);
        }
        entityManager.remove(tag);
    }
}
