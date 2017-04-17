package com.jesse.pricingproject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.jesse.pricingproject.dao.TagDao;
import com.jesse.pricingproject.domain.Tag;

@CrossOrigin(origins = "http://localhost:9000")
@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagDao tagDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getAllProjects() {
        List<Tag> projects = tagDao.findAll();
        String json = new Gson().toJson(projects);
        return json;
    }

    @RequestMapping(value = "/search/{term}", method = RequestMethod.GET)
    public String searchProject(@PathVariable String term) {
        return new Gson().toJson(tagDao.search(term));
    }

    @RequestMapping(value = "projectid/{projId}", method = RequestMethod.GET)
    public String returnTagsForProjectId(@PathVariable Long projId) {
        return new Gson().toJson(tagDao.findTagsByProjectId(projId));
    }

    @RequestMapping(value = "projectid/{projIds}", method = RequestMethod.GET)
    public String returnTagsForProjectId(@PathVariable Long[] projIds) {
        return new Gson().toJson(tagDao.findTagsByProjecstIds(projIds));
    }

    @RequestMapping(value = "/search/", method = RequestMethod.GET)
    public String searchProject() {
        return new Gson().toJson(tagDao.findAll());
    }

}