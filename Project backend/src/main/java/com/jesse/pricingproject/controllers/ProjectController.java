package com.jesse.pricingproject.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.jesse.pricingproject.dao.ProjectDao;
import com.jesse.pricingproject.domain.Project;

@CrossOrigin(origins = "http://localhost:9000")
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectDao projectDao;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getAllProjects() {
        List<Project> projects = projectDao.findAll();
        String json = new Gson().toJson(projects);
        return json;
    }

    @RequestMapping(value = "/all/withtags", method = RequestMethod.GET)
    public String getAllProjectsWithTags() {
        List<Project> projects = projectDao.findAllWithTags();
        String json = new Gson().toJson(projects);
        return json;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<Project> getProjects() {
        Project project = new Project();
        project.setDescription("kjdsflkjdlf");
        project.setTitle("khsfk");

        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @RequestMapping(value = "/random", method = RequestMethod.GET)
    public String getRandomProject() {
        List<Project> projects = projectDao.findRandom();
        String json = new Gson().toJson(projects);
        return json;
    }

    @RequestMapping(value = "/testmap", method = RequestMethod.GET)
    public String getTest() {
        List<String> test1 = new ArrayList<String>();
        test1.add("1,2,3");
        Map<String, String> test = new HashMap<String, String>();
        test.put("1", "1,2,3");
        test.put("22", "1,3");
        test.put("112", "3");
        String json = new Gson().toJson(test1);
        return json;
    }

    @RequestMapping(value = "/search/{term}", method = RequestMethod.GET)
    public String searchProject(@PathVariable String term) {
        return new Gson().toJson(projectDao.search(term));
    }

    @RequestMapping(value = "/tag/{tagId}", method = RequestMethod.GET)
    public String searchProjectByTagId(@PathVariable Long tagId) {
        return new Gson().toJson(projectDao.findProjectAndTagsByTagId(tagId));
    }

    @RequestMapping(value = "/withtags/{projectId}", method = RequestMethod.GET)
    public String searchProjectAndTagsByProjectId(@PathVariable Long projectId) {
        return new Gson().toJson(projectDao.findProjectAndTagsByProjectId(projectId));
    }

    @RequestMapping(value = "/search/", method = RequestMethod.GET)
    public String searchProject() {
        return new Gson().toJson(projectDao.findAll());
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    //the @RequestBody method parameter annotation should bind the json value in the HTTP request body
    //to the java object by using a HttpMessageConverter. 
    public ResponseEntity<Project> saveProject(@RequestBody Project project) {

        System.out.println(project.toString());
        Project proj = project;
        projectDao.save(proj);
        //ResponseEntity is a class that allows you to modify request and 
        //response headers. An important design aspect of REST services
        //is also to return the proper HTTP status code and in this case a 200. 
        return new ResponseEntity<Project>(proj, HttpStatus.OK);
    }
}
