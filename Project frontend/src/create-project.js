import 'fetch';
import $ from 'bootstrap';
import {
  HttpClient,
  json
} from 'aurelia-fetch-client';

import {
  DialogService
} from 'aurelia-dialog';
import {
  inject
} from 'aurelia-framework';
import {
  Prompt
} from './components/modal/my-modal';

let httpClient = new HttpClient();

@inject(DialogService)

export class App {
  // all the commented console.logs were for debugging
  constructor(dialogService) {
    this.dialogService = dialogService;
  }

  openModal() {
    this.dialogService.open({
      viewModel: Prompt,
      model: 'Successfully saved'
    }).then(response => {
      console.log(response);
      if (!response.wasCancelled) {
        console.log('OK');
      } else {
        console.log('cancelled');
      }
      console.log(response.output);
    });
  }

  tagIdHolder = null;

  //array of all selected tag OBJECTS shown to user
  selectedTagTitlesArray = [];

  project = {
    title: "",
    description: "",
    tagIds: []
  };

  postData() {
    if (this.project.title != "") {
      // console.log(JSON.stringify(this.project) + '  before posting');
      httpClient.fetch('http://www.localhost:8080/project/save', {
        method: "post",
        //turn my project object into json befor sending it
        body: json(this.project)
        //expecting json response back
      }).then(response => response.json()).then(this.openModal());
      //clear everything for next project
      this.project.title = "";
      this.project.description = "";
      this.tagIds = [];
      this.selectedTagTitlesArray = [];
    }
  }

  addTag(tag) {
    let selectedTagTitleObject = {
      id: tag.id,
      title: tag.title
    }
    console.log(tag);
    if (!this.project.tagIds.includes(tag.id)) {
      //add tag id's to project array.
      this.project.tagIds.push(tag.id);
      //add tag object to selected tag array, to show vivsually to user
      this.selectedTagTitlesArray.push(selectedTagTitleObject);
      // for debugging 
      // console.log("The object was saved as :" + selectedTagTitleObject.title);
      // console.log("The array is now :" + this.selectedTagTitlesArray);
    }
  }

  removeTag(tagObject) {
    // ugly code to remove a tag
    let theId = tagObject.id; 
    var i = this.project.tagIds.length;
    while (i--) {
      if (this.project.tagIds[i] === this.theId) this.project.tagIds.splice(i, 1);

            if (this.selectedTagTitlesArray[i].id === theId) console.log("Removed: " + this.selectedTagTitlesArray.splice(i, 1));
    }
    ///////////////////////////
    // for (var i = 0; i <= this.project.tagIds.length - 1; i++) {
    //   if (this.selectedTagTitlesArray[i].id === theId) console.log("Removed: " + this.selectedTagTitlesArray.splice(i, 1));
    // }
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
}
