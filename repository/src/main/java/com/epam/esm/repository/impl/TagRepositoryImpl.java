package com.epam.esm.repository.impl;

import com.epam.esm.common.Certificate;
import com.epam.esm.common.ErrorDefinition;
import com.epam.esm.common.Tag;
import com.epam.esm.common.exception.EntityNotFoundException;
import com.epam.esm.repository.TagRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class TagRepositoryImpl implements TagRepository {

    private static final String JPQL_SELECT_ALL = "FROM Tag";
    private static final String JPQL_SELECT_BY_NAME = "from Tag where name in (:names)";
    private static final String JPQL_DELETE = "delete Tag where id=:id";
    private static final String JPQL_SELECT_CERTIFICATE_TAGS = "select c.tags from Certificate c where c.id=:id";

    @PersistenceContext
    private EntityManager entityManager;

    public List<Tag> getAllTags() {
        return entityManager.createQuery(JPQL_SELECT_ALL, Tag.class).getResultList();
    }

    public Tag getTag(Long id) {
        Tag tag = entityManager.find(Tag.class, id);
        if (tag == null) {
            throw new EntityNotFoundException(ErrorDefinition.TAG_NOT_FOUND, id);
        }
        return tag;
    }

    @SuppressWarnings("unchecked")
    public Map<Long, List<Tag>> getCertificatesTags(List<Certificate> certificates) {
        List<Long> ids = certificates.stream().map(Certificate::getId).collect(Collectors.toList());
        Map<Long, List<Tag>> certificateTag = new HashMap<>();

        //todo: Batch?
        for(Long id : ids) {
            List<Tag> tags = entityManager.createQuery(JPQL_SELECT_CERTIFICATE_TAGS)
                    .setParameter("id", id)
                    .getResultList();
            certificateTag.put(id, tags);
        }

        return certificateTag;
    }

    @SuppressWarnings("unchecked")
    public List<Tag> getCertificateTags(Long certificateId) {
        return entityManager.createQuery(JPQL_SELECT_CERTIFICATE_TAGS)
                .setParameter("id", certificateId)
                .getResultList();
    }

    public List<Tag> getTagsByName(List<Tag> tagList) {
        List<String> names = tagList.stream().map(Tag::getName).collect(Collectors.toList());
        return entityManager.createQuery(JPQL_SELECT_BY_NAME, Tag.class)
                .setParameter("names", names)
                .getResultList();
    }

    public Tag createNewTag(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    public void createNewTags(List<Tag> tags) {
        tags = tags.stream().distinct().collect(Collectors.toList());
        tags.forEach(t -> entityManager.persist(t));
    }

    public void createTagsIfNonExist(List<Tag> tags) {
        List<Tag> existingTags = getTagsByName(tags);

        List<Tag> nonexistentTags = tags.stream()
                .filter(exist -> existingTags.stream()
                        .noneMatch(t -> t.getName().equals(exist.getName())))
                .collect(Collectors.toList());

        createNewTags(nonexistentTags);
    }

    public void deleteTag(Long id) {
        Query query = entityManager.createQuery(JPQL_DELETE);
        query.setParameter("id", id);
        if (query.executeUpdate() != 1) {
            throw new EntityNotFoundException(ErrorDefinition.TAG_NOT_FOUND, id);
        }
    }
}
