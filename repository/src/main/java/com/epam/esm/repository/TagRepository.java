package com.epam.esm.repository;

import com.epam.esm.common.TagDTO;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TagRepository {

    private static final String RETRIEVE_TAGS = "SELECT * FROM tag WHERE name IN (";
    private static final String ADD_NEW_TAGS = "INSERT INTO tag(name) VALUES(?)";

    private final JdbcTemplate jdbcTemplate;

    public TagRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TagDTO> getTagsByName(List<TagDTO> tagDTOList) {
        StringBuilder retrieveTagByNameSql = new StringBuilder(RETRIEVE_TAGS);

        tagDTOList.forEach(tag -> retrieveTagByNameSql.append("'").append(tag.getName()).append("',"));
        retrieveTagByNameSql.replace(retrieveTagByNameSql.lastIndexOf(","), retrieveTagByNameSql.length(), ")");

        return jdbcTemplate.query(retrieveTagByNameSql.toString(), BeanPropertyRowMapper.newInstance(TagDTO.class));
    }

    public void saveNewTags(List<String> tagNames) {
        jdbcTemplate.batchUpdate(ADD_NEW_TAGS, new BatchPreparedStatementSetter() {
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
}
