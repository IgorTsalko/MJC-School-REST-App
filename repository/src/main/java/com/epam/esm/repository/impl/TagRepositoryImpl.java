package com.epam.esm.repository.impl;

import com.epam.esm.common.Certificate;
import com.epam.esm.common.ErrorDefinition;
import com.epam.esm.common.Tag;
import com.epam.esm.common.exception.EntityNotFoundException;
import com.epam.esm.repository.TagRepository;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class TagRepositoryImpl implements TagRepository {

    private static final String SQL_SELECT_BY_ID = "SELECT * FROM tag WHERE id=?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM tag";
    private static final String SQL_SELECT_CERTIFICATE_TAGS =
            "SELECT gift_certificate_id, tag_id, tag.name FROM gift_certificate_tag gct JOIN tag ON gct.tag_id = tag.id " +
                    "WHERE gift_certificate_id IN (:ids)";
    private static final String SQL_SELECT_BY_CERTIFICATE_ID = "SELECT id, name FROM gift_certificate_tag gct " +
            "JOIN tag ON gct.tag_id = tag.id WHERE gct.gift_certificate_id=?";
    private static final String SQL_SELECT_BY_NAME = "SELECT * FROM tag WHERE name IN (:names)";
    private static final String SQL_INSERT = "INSERT INTO tag(name) VALUES(:name)";
    private static final String SQL_DELETE = "DELETE FROM tag WHERE id=?";

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SessionFactory sessionFactory;

    public TagRepositoryImpl(JdbcTemplate jdbcTemplate,
                             NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                             SessionFactory sessionFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.sessionFactory = sessionFactory;
    }

    public List<Tag> getAllTags() {
        return jdbcTemplate.query(SQL_SELECT_ALL, BeanPropertyRowMapper.newInstance(Tag.class));
    }

    public Tag getTag(Long id) {
        Tag tag = sessionFactory.getCurrentSession().get(Tag.class, id);
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
        return jdbcTemplate
                .query(SQL_SELECT_BY_CERTIFICATE_ID, BeanPropertyRowMapper.newInstance(Tag.class), certificateId);
    }

    public List<Tag> getTagsByName(List<Tag> tagList) {
        List<String> names = tagList.stream().map(Tag::getName).collect(Collectors.toList());
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("names", names);

        return namedParameterJdbcTemplate.query(SQL_SELECT_BY_NAME, params, BeanPropertyRowMapper.newInstance(Tag.class));
    }

    public Tag createNewTag(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate
                .update(SQL_INSERT, new MapSqlParameterSource("name", tag.getName()), keyHolder);
        return tag.setId(((Number) keyHolder.getKeys().get("id")).longValue());
    }

    public void createNewTags(List<Tag> tags) {
        SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(tags);
        namedParameterJdbcTemplate.batchUpdate(SQL_INSERT, params);
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
        if (jdbcTemplate.update(SQL_DELETE, id) == 0) {
            throw new EntityNotFoundException(ErrorDefinition.TAG_NOT_FOUND, id);
        }
    }
}
