package com.epam.esm.repository;

import com.epam.esm.common.TagDTO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagRepository {

    private static final String RETRIEVE_TAGS = "SELECT * FROM tag WHERE";
    private static final String ADD_NEW_TAGS = "INSERT INTO tag(name) VALUES";

    private final JdbcTemplate jdbcTemplate;

    public TagRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TagDTO> getTagsByName(List<TagDTO> tagDTOList) {
        StringBuilder retrieveTagByNameSql = new StringBuilder(RETRIEVE_TAGS);

        tagDTOList.forEach(tag -> retrieveTagByNameSql.append(" name='").append(tag.getName()).append("' OR"));
        retrieveTagByNameSql.delete(retrieveTagByNameSql.lastIndexOf(" OR"), retrieveTagByNameSql.length());

        return jdbcTemplate.query(retrieveTagByNameSql.toString(), BeanPropertyRowMapper.newInstance(TagDTO.class));
    }

    public void saveNewTags(List<String> tags) {
        StringBuilder addNewTagsSql = new StringBuilder(ADD_NEW_TAGS);

        for (String tagName : tags) {
            addNewTagsSql.append(" ('").append(tagName).append("'),");
        }
        addNewTagsSql.delete(addNewTagsSql.lastIndexOf(","), addNewTagsSql.length());

        jdbcTemplate.update(addNewTagsSql.toString());
    }
}
