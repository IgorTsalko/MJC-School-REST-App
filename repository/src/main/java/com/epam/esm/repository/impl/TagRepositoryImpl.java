package com.epam.esm.repository.impl;

import com.epam.esm.common.Certificate;
import com.epam.esm.common.ErrorDefinition;
import com.epam.esm.common.Tag;
import com.epam.esm.common.exception.EntityNotFoundException;
import com.epam.esm.repository.TagRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class TagRepositoryImpl implements TagRepository {

    private static final String SQL_SELECT_CERTIFICATE_TAGS =
            "SELECT gift_certificate_id, tag_id, tag.name FROM gift_certificate_tag gct JOIN tag ON gct.tag_id = tag.id " +
                    "WHERE gift_certificate_id IN (:ids)";

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    public TagRepositoryImpl(JdbcTemplate jdbcTemplate,
                             NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Tag> getAllTags() {
        return entityManager.createQuery("FROM Tag", Tag.class).getResultList();
    }

    public Tag getTag(Long id) {
        Tag tag = entityManager.find(Tag.class, id);
        if (tag == null) {
            throw new EntityNotFoundException(ErrorDefinition.TAG_NOT_FOUND, id);
        }
        return tag;
    }

    public Map<Long, List<Tag>> getCertificatesTags(List<Certificate> certificates) {
        Map<Long, List<Tag>> certificateTag = new HashMap<>();

        if (!certificates.isEmpty()) {
            List<Long> ids = certificates.stream().map(Certificate::getId).collect(Collectors.toList());
            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("ids", ids);

            namedParameterJdbcTemplate.query(SQL_SELECT_CERTIFICATE_TAGS, params, rs -> {
                do {
                    Long certificateId = rs.getLong(1);
                    Tag tag = new Tag().setId(rs.getLong(2)).setName(rs.getString(3));
                    certificateTag.computeIfAbsent(certificateId, k -> new ArrayList<>());
                    certificateTag.get(certificateId).add(tag);
                } while (rs.next());
            });
        }

        return certificateTag;
    }

    public List<Tag> getCertificateTags(Long certificateId) {
        //todo: EXCEPTION if I use second param Tag.class for createQuery:
        // Type specified for TypedQuery [com.epam.esm.common.Tag] is incompatible with query return type [interface java.util.Collection]
        return entityManager.createQuery("select c.tags from Certificate c where c.id=:id")
                .setParameter("id", certificateId)
                .getResultList();
    }

    public List<Tag> getTagsByName(List<Tag> tagList) {
        List<String> names = tagList.stream().map(Tag::getName).collect(Collectors.toList());
        return entityManager.createQuery("from Tag where name in (:names)", Tag.class)
                .setParameter("names", names)
                .getResultList();
    }

    public Tag createNewTag(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    public void createNewTags(List<Tag> tags) {
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
        Query query = entityManager.createQuery("delete Tag where id=:id");
        query.setParameter("id", id);
        if (query.executeUpdate() == 0) {
            throw new EntityNotFoundException(ErrorDefinition.TAG_NOT_FOUND, id);
        }
    }
}
