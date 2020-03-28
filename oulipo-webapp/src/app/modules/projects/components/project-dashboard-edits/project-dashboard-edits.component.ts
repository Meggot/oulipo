import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { ProjectModel } from 'src/app/modules/shared/models/project.model';
import { Store } from '@ngrx/store';
import { AppState } from 'src/app/store/app.reducers';
import { CopyModel } from 'src/app/modules/shared/models/copy.model';
import { EditModel } from 'src/app/modules/shared/models/edit.model';
import * as ProjectActions from '../../store/project.actions'
import { DiffMatchPatch } from 'ng-diff-match-patch';
@Component({
  selector: 'app-project-dashboard-edits',
  templateUrl: './project-dashboard-edits.component.html',
  styleUrls: ['./project-dashboard-edits.component.css']
})
export class ProjectDashboardEditsComponent implements OnInit {

  viewingCopy: Observable<CopyModel>
  previewingEdit = false;
  postText: any;

  constructor(private store: Store<AppState>) {
    this.viewingCopy = store.select('projects', 'viewingCopy')
  }

  ngOnInit() {
  }

  previewEdit(edit: EditModel) {
    this.previewingEdit = !this.previewingEdit
    if (this.previewingEdit) {
      let diffMatch = new DiffMatchPatch()
      // const patch = diffMatch.patch_apply(viewingCopy.value, postText);
      // const patchToString = diffMatch.patch_toText(patch);
      // this.copyDiv.nativeElement.innerText = viewingCopy.value

      let diffsFromDelta = diffMatch.patch_fromText(edit.delta)
      let sub = this.viewingCopy.subscribe(
        (copy: CopyModel) => {
          this.postText = diffMatch.patch_apply(diffsFromDelta, copy.value)[0]
        }
      )
      sub.unsubscribe();
    }
  }

  onActionEdit(edit: EditModel, action: string) {
    switch (action) {
      case 'APPROVE':
        this.store.dispatch(new ProjectActions.ActionOnEdit({ editId: edit.idField, action: action }));
        return;
      case 'DECLINE':
        this.store.dispatch(new ProjectActions.ActionOnEdit({ editId: edit.idField, action: action }));
        return;
      default:
        return;
    }
  }

}
