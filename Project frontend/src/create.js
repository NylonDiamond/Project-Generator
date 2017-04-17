import {
  inject
} from 'aurelia-framework';
import {
  HttpClient,
  json
} from 'aurelia-fetch-client';
import 'fetch';


@inject(HttpClient)
export class create {

  heading = 'Create Project';
  users = [];

  constructor(http) {
    http.configure(config => {
      config
        .useStandardConfiguration()
        .withBaseUrl('https://api.github.com/');
    });

    this.http = http;
  }
  //get all the tags
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

  activate() {
    return this.http.fetch('users')
      .then(response => response.json())
      .then(users => this.users = users);
  }
}
