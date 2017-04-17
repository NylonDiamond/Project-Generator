package com.jesse.pricingproject.domain;

import java.io.Serializable;
import java.util.List;

public class Project implements Comparable<Project>, Serializable {

    private static final long serialVersionUID = 540567218318333786L;

    long id;
    String title;
    String description;
    List<Long> tagIds;
    List<Tag> tags;

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Long> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<Long> tagIds) {
        this.tagIds = tagIds;
    }

    public Project() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Project title: " + this.title + "\n" + "Project Description: " + this.description;
    }

    @Override
    public int compareTo(Project project) {
        return 0;
    }
}
