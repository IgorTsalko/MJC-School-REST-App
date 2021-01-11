package com.epam.esm.repository;

import com.epam.esm.common.TagDTO;
import com.epam.esm.common.exception.EntityNotFoundException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TagRepository {

    private static final String RETRIEVE_TAG_BY_ID = "SELECT * FROM tag WHERE id=?";
    private static final String RETRIEVE_TAGS_BY_CERTIFICATE_ID = "SELECT id, name FROM gift_certificate_tag gct " +
            "JOIN tag ON gct.tag_id = tag.id WHERE gct.gift_certificate_id=?";
    private static final String RETRIEVE_TAGS = "SELECT * FROM tag WHERE name IN (:names)";
    private static final String SAVE_NEW_TAG = "INSERT INTO tag(name) VALUES(?)";
    private static final String DELETE_TAG = "DELETE FROM tag WHERE id=?";

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TagRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public TagDTO getTag(int id) {
        return jdbcTemplate.query(RETRIEVE_TAG_BY_ID, BeanPropertyRowMapper.newInstance(TagDTO.class), id)
                .stream().findAny().orElseThrow(() -> new EntityNotFoundException(id));
    }

    public List<TagDTO> getCertificateTags(int certificateId) {
        return jdbcTemplate
                .query(RETRIEVE_TAGS_BY_CERTIFICATE_ID, BeanPropertyRowMapper.newInstance(TagDTO.class), certificateId);
    }

    public List<TagDTO> getTagsByName(List<TagDTO> tagDTOList) {
        List<String> names = tagDTOList.stream().map(TagDTO::getName).collect(Collectors.toList());
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("names", names);

        return namedParameterJdbcTemplate.query(RETRIEVE_TAGS, params, BeanPropertyRowMapper.newInstance(TagDTO.class));
    }

    public TagDTO saveNewTag(TagDTO tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(
                SAVE_NEW_TAG,
                new MapSqlParameterSource().addValue("name", tag.getName()),
                keyHolder);

        return tag.setId((Integer) keyHolder.getKeys().get("id"));
    }

    private void saveNewTags(List<String> tagNames) {
        jdbcTemplate.batchUpdate(SAVE_NEW_TAG, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, tagNames.get(i));
            }

            @Override
            public int getBatchSize() {
                return tagNames.size();
            }
        });
    }

    public void saveTagsIfNonExist(List<TagDTO> tags) {
        List<TagDTO> existingTags = getTagsByName(tags);

        if (existingTags.size() != tags.size()) {
            List<String> sentTagsName = tags.stream().map(TagDTO::getName).collect(Collectors.toList());
            List<String> existingTagsName = existingTags.stream().map(TagDTO::getName).collect(Collectors.toList());
            List<String> nonexistentTagsName = sentTagsName.stream()
                    .filter(tagName -> !existingTagsName.contains(tagName))
                    .collect(Collectors.toList());

            saveNewTags(nonexistentTagsName);
        }
    }

    public void deleteTag(int id) {
        if (jdbcTemplate.update(DELETE_TAG, id) == 0) {
            throw new EntityNotFoundException(id);
        }
    }
}
