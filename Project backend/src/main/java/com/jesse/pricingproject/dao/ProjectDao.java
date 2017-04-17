package com.jesse.pricingproject.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.jesse.pricingproject.daomapper.ProjectMapper;
import com.jesse.pricingproject.daomapper.TagMapper;
import com.jesse.pricingproject.domain.Project;
import com.jesse.pricingproject.domain.Tag;

@Repository("ProjectDao")
public class ProjectDao {

    @Autowired
    protected NamedParameterJdbcTemplate namedJdbcTemplate;

    public void save(Project project) {
        String sql = "INSERT INTO project (project_title, project_description) VALUES ( :title, :description) RETURNING project_id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", project.getTitle());
        params.addValue("description", project.getDescription());
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedJdbcTemplate.update(sql, params, keyHolder, new String[] { "ID" });
        System.out.println("Project id is : " + keyHolder.getKey().longValue());

        long project_id = keyHolder.getKey().longValue();

        System.out.println("Project id is again : " + project_id);
        Set<Long> uniqueIds = new HashSet<Long>(project.getTagIds());
        System.out.println("Made it through project dao first portion");
        if (uniqueIds != null) {
            for (long tag : uniqueIds) {
                System.out.println("The first tag id is : " + tag);
                String sql2 = "INSERT INTO project_tag (project_id, tag_id) VALUES ( :project_id, :tag_id)";
                MapSqlParameterSource params2 = new MapSqlParameterSource();
                params2.addValue("project_id", project_id);
                params2.addValue("tag_id", tag);
                namedJdbcTemplate.update(sql2, params2);
            }
        }
    }

    public List<Project> findProjectAndTagsByTagId(Long tagId) {
        String sql = "SELECT project.project_id, project_title, project_description FROM project, project_tag WHERE project.project_id = project_tag.project_id AND project_tag.tag_id = :tag_id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("tag_id", tagId);
        List<Project> projects = namedJdbcTemplate.query(sql, params, new ProjectMapper());

        for (int x = 0; x < projects.size(); x++) {
            String sqlTags = "SELECT tag.tag_id, tag_title FROM tag, project_tag WHERE tag.tag_id = project_tag.tag_id AND project_tag.project_id = :proj_id";
            MapSqlParameterSource paramsTags = new MapSqlParameterSource();
            paramsTags.addValue("proj_id", projects.get(x).getId());
            List<Tag> tags = namedJdbcTemplate.query(sqlTags, paramsTags, new TagMapper());
            projects.get(x).setTags(tags);
        }
        return projects;
    }

    //returns tag titles as well
    public List<Project> findProjectAndTagsByProjectId(Long projId) {
        //        String sql = "SELECT project.project_id, project.project_title, project.project_description FROM project, project_tag, tag WHERE project.project_id = project_tag.project_id AND project_tag.tag_id = tag.tag_id AND project.project_id =  :proj_id";

        String sql = "SELECT * FROM project WHERE project_id = :proj_id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("proj_id", projId);
        List<Project> projects = namedJdbcTemplate.query(sql, params, new ProjectMapper());

        String sqlTags = "SELECT tag.tag_id, tag_title FROM tag, project_tag WHERE tag.tag_id = project_tag.tag_id AND project_tag.project_id = :proj_id";
        MapSqlParameterSource paramsTags = new MapSqlParameterSource();
        paramsTags.addValue("proj_id", projId);
        List<Tag> tags = namedJdbcTemplate.query(sqlTags, paramsTags, new TagMapper());

        projects.get(0).setTags(tags);
        return projects;

        //        MapSqlParameterSource params = new MapSqlParameterSource();
        //        params.addValue("proj_id", projId);
        //        return namedJdbcTemplate.query(sql, params, new ProjectMapper());
    }

    public List<Project> findProjectById(Long id) {
        String sql = "SELECT * FROM project WHERE project_id = :proj_id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("proj_id", id);
        return namedJdbcTemplate.query(sql, params, new ProjectMapper());
    }

    public List<Project> findAll() {
        String sql = "SELECT * FROM project";
        MapSqlParameterSource params = new MapSqlParameterSource();
        return namedJdbcTemplate.query(sql, params, new ProjectMapper());
    }

    public List<Project> findAllWithTags() {
        String sql = "SELECT * FROM project";
        MapSqlParameterSource params = new MapSqlParameterSource();
        List<Project> projects = namedJdbcTemplate.query(sql, params, new ProjectMapper());

        for (int x = 0; x < projects.size(); x++) {
            String sqlTags = "SELECT tag.tag_id, tag_title FROM tag, project_tag WHERE tag.tag_id = project_tag.tag_id AND project_tag.project_id = :proj_id";
            MapSqlParameterSource paramsTags = new MapSqlParameterSource();
            paramsTags.addValue("proj_id", projects.get(x).getId());
            List<Tag> tags = namedJdbcTemplate.query(sqlTags, paramsTags, new TagMapper());
            projects.get(x).setTags(tags);
        }
        return projects;
    }

    public List<Project> findRandom() {
        String sql = "SELECT * FROM project ORDER BY RANDOM() LIMIT 1";
        MapSqlParameterSource params = new MapSqlParameterSource();
        return namedJdbcTemplate.query(sql, params, new ProjectMapper());
    }

    public List<Project> search(String term) {
        String sql = "SELECT * FROM project WHERE LOWER(project_title) LIKE '%' || LOWER(:term) || '%'";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("term", term);
        List<Project> projects = namedJdbcTemplate.query(sql, params, new ProjectMapper());

        for (int x = 0; x < projects.size(); x++) {
            String sqlTags = "SELECT tag.tag_id, tag_title FROM tag, project_tag WHERE tag.tag_id = project_tag.tag_id AND project_tag.project_id = :proj_id";
            MapSqlParameterSource paramsTags = new MapSqlParameterSource();
            paramsTags.addValue("proj_id", projects.get(x).getId());
            List<Tag> tags = namedJdbcTemplate.query(sqlTags, paramsTags, new TagMapper());

            projects.get(x).setTags(tags);
        }

        return projects;
    }

}