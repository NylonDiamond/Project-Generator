import {
  DialogService
} from 'aurelia-dialog';
import {
  inject
} from 'aurelia-framework';
import {
  Prompt
} from './components/modal/my-modal';

export class App {
  constructor(dialogService) {
    this.dialogService = dialogService;
  }

  openModal() {
    this.dialogService.open({
      viewModel: Prompt,
      model: 'Are you sure?'
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

  configureRouter(config, router) {
    config.title = 'Project Generator';
    config.map([{
        route: ['', 'welcome'],
        name: 'welcome',
        moduleId: 'welcome',
        nav: true,
        title: 'Find Projects'
      },
      {
        route: 'create-project',
        name: 'create-project',
        moduleId: 'create-project',
        nav: true,
        title: 'Create Project'
      }
    ]);

    this.router = router;
  }
}
