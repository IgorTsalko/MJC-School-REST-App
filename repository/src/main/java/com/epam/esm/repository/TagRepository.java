package com.epam.esm.repository;

import com.epam.esm.common.ErrorDefinition;
import com.epam.esm.common.TagDTO;
import com.epam.esm.common.exception.EntityNotFoundException;
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
public class TagRepository {

    private static final String RETRIEVE_TAG_BY_ID = "SELECT * FROM tag WHERE id=?";
    private static final String RETRIEVE_ALL_TAGS = "SELECT * FROM tag";
    private static final String RETRIEVE_ALL_CERTIFICATE_TAGS =
            "SELECT gift_certificate_id, tag_id, tag.name FROM gift_certificate_tag gct JOIN tag ON gct.tag_id = tag.id";
    private static final String RETRIEVE_TAGS_BY_CERTIFICATE_ID = "SELECT id, name FROM gift_certificate_tag gct " +
            "JOIN tag ON gct.tag_id = tag.id WHERE gct.gift_certificate_id=?";
    private static final String RETRIEVE_TAGS = "SELECT * FROM tag WHERE name IN (:names)";
    private static final String SAVE_NEW_TAG = "INSERT INTO tag(name) VALUES(:name)";
    private static final String DELETE_TAG = "DELETE FROM tag WHERE id=?";

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TagRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<TagDTO> getAllTags() {
        return jdbcTemplate.query(RETRIEVE_ALL_TAGS, BeanPropertyRowMapper.newInstance(TagDTO.class));
    }

    public TagDTO getTag(int id) {
        return jdbcTemplate.query(RETRIEVE_TAG_BY_ID, BeanPropertyRowMapper.newInstance(TagDTO.class), id)
                .stream().findAny().orElseThrow(() -> new EntityNotFoundException(ErrorDefinition.TAG_NOT_FOUND, id));
    }

    public Map<Integer, List<TagDTO>> getAllCertificateTags() {
        Map<Integer, List<TagDTO>> certificateTag = new HashMap<>();

        jdbcTemplate.query(RETRIEVE_ALL_CERTIFICATE_TAGS, rs -> {
            do {
                int certificateId = rs.getInt(1);
                TagDTO tagDTO = new TagDTO().setId(rs.getInt(2)).setName(rs.getString(3));
                certificateTag.computeIfAbsent(certificateId, k -> new ArrayList<>());
                certificateTag.get(certificateId).add(tagDTO);
            } while (rs.next());
        });

        return certificateTag;
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
        namedParameterJdbcTemplate
                .update(SAVE_NEW_TAG, new MapSqlParameterSource("name", tag.getName()), keyHolder);
        return tag.setId((Integer) keyHolder.getKeys().get("id"));
    }

    public void saveNewTags(List<TagDTO> tags) {
        SqlParameterSource[] params =  SqlParameterSourceUtils.createBatch(tags);
        namedParameterJdbcTemplate.batchUpdate(SAVE_NEW_TAG, params);
    }

    public void saveTagsIfNonExist(List<TagDTO> tags) {
        List<TagDTO> existingTags = getTagsByName(tags);

        List<TagDTO> nonexistentTags = tags.stream()
                .filter(exist -> existingTags.stream()
                        .noneMatch(t -> t.getName().equals(exist.getName())))
                .collect(Collectors.toList());

        saveNewTags(nonexistentTags);
    }

    public void deleteTag(int id) {
        if (jdbcTemplate.update(DELETE_TAG, id) == 0) {
            throw new EntityNotFoundException(ErrorDefinition.TAG_NOT_FOUND, id);
        }
    }
}
