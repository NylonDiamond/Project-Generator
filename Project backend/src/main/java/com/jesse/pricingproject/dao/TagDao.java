package com.jesse.pricingproject.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.jesse.pricingproject.daomapper.TagMapper;
import com.jesse.pricingproject.domain.Tag;

@Repository("TagDao")
public class TagDao {

    @Autowired
    protected NamedParameterJdbcTemplate namedJdbcTemplate;

    //    public void save(Project project) {
    //        String sql = "INSERT INTO contacttable (contactfirstname, contactlastname, contactphonenumber, contactemail) VALUES ( :contactfirstname, :contactlastname, :contactphonenumber, :contactemail)";
    //
    //        MapSqlParameterSource params = new MapSqlParameterSource();
    //
    //        params.addValue("contactfirstname", contact.getFirstName());
    //        params.addValue("contactlastname", contact.getLastName());
    //        params.addValue("contactphonenumber", contact.getPhone());
    //        params.addValue("contactemail", contact.getEmail());
    //
    //        namedJdbcTemplate.update(sql, params);
    //
    //    }

    public List<Tag> getTagById(Long id) {
        String sql = "SELECT * FROM tag WHERE tag_id = :tag_id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("tag_id", id);
        return namedJdbcTemplate.query(sql, params, new TagMapper());
    }

    public List<Tag> findAll() {
        String sql = "SELECT * FROM tag";
        MapSqlParameterSource params = new MapSqlParameterSource();
        return namedJdbcTemplate.query(sql, params, new TagMapper());
    }

    public List<Tag> findTagsByProjecstIds(Long[] projIds) {
        List<Tag> tags = null;
        for (long id : projIds) {
            List<Tag> currentTag = null;
            String sql = "SELECT tag.tag_id, tag_title FROM tag, project_tag WHERE tag.tag_id = project_tag.tag_id AND project_tag.project_id = :proj_ids";
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("proj_ids", id);
            currentTag = (namedJdbcTemplate.query(sql, params, new TagMapper()));
            tags.add(currentTag.get(0));
        }
        return tags;
    }

    public List<Tag> findTagsByProjectId(Long proj_id) {
        String sql = "SELECT tag.tag_id, tag_title FROM tag, project_tag WHERE tag.tag_id = project_tag.tag_id AND project_tag.project_id = :proj_id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("proj_id", proj_id);
        return namedJdbcTemplate.query(sql, params, new TagMapper());
    }

    public List<Tag> search(String term) {
        String sql = "SELECT * FROM tag WHERE LOWER(tag_title) LIKE '%' || LOWER(:term) || '%'";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("term", term);
        return namedJdbcTemplate.query(sql, params, new TagMapper());
    }

}