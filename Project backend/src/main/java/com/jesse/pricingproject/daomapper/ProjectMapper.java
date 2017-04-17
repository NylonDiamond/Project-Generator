package com.jesse.pricingproject.daomapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jesse.pricingproject.domain.Project;

public class ProjectMapper implements RowMapper<Project> {

    @Override
    public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
        Project project = new Project();

        project.setId(rs.getLong("project_id"));
        project.setDescription(rs.getString("project_description"));
        project.setTitle(rs.getString("project_title"));

        return project;
    }

}
