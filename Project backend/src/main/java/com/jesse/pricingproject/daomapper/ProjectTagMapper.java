package com.jesse.pricingproject.daomapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jesse.pricingproject.domain.Tag;

public class ProjectTagMapper implements RowMapper<Tag> {

    @Override
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
        Tag tag = new Tag();

        tag.setId(rs.getLong("tag_id"));
        tag.setTitle(rs.getString("tag_title"));

        return tag;
    }

}
