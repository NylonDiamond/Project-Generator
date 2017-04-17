import 'fetch';
import {
  HttpClient,
  json
} from 'aurelia-fetch-client';

let httpClient = new HttpClient();

export class Welcome {

  heading = 'Project Finder';

  getData() {
    httpClient.fetch('http://localhost:8080/project/all/withtags')
      .then(response => response.json())
      .then(d => {
        this.projects = d
      }).then(x => console.log(this.projects));
  }

  getProjectByTagId(tag) {
    httpClient.fetch('http://localhost:8080/project/tag/' + tag.id)
      .then(response => response.json())
      .then(d => {
        this.projects = d
      }).then(data => console.log(this.projects));
  }

  getRandomProject() {
    httpClient.fetch('http://localhost:8080/project/random')
      .then(response => response.json())
      .then(d => {
        this.projects = d
      }).then(x => console.log(this.projects)).then(x => this.getProjectTags(x)).then(x => console.log("ended with " + this.projects));
  }

  getProjectTags(projects) {
    httpClient.fetch('http://localhost:8080/project/withtags/' + this.projects[0].id)
      .then(response => response.json())
      .then(d => {
        this.projects = d
      }).then(x => console.log(this.projects));
  }

  searchProject() {
    this.projects = [];
    httpClient.fetch('http://localhost:8080/project/search/' + this.term)
      .then(response => response.json())
      .then(t => {
        this.projects = t;
      }).then(x => this.term = "").catch(error => this.projects = [{
        title: "No results",
        description: null
      }]);
  }

  created() {
    httpClient.fetch('http://localhost:8080/tag/all')
      .then(response => response.json())
      .then(t => {
        this.tags = t
      }).then(data => console.log(this.tags)).catch(error => this.tags = [{
        title: "Error retrieving tags :()"
      }, {
        title: ""
      }]);
  }
}
