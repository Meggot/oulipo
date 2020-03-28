import { Component, Input, OnInit, ViewChild, ElementRef } from '@angular/core';
import { CopyModel } from '../../../../shared/models/copy.model';
import { AppState } from 'src/app/store/app.reducers';
import { Store, State } from '@ngrx/store';
import { ProjectModel } from 'src/app/modules/shared/models/project.model';
import { Observable } from 'rxjs';
import * as fromProjects from '../../../store/projects.reducers';
import * as fromAccounts from '../../../../accounts/store/account.reducers';
import { map, subscribeOn } from 'rxjs/operators';
import * as ProjectActions from '../../../store/project.actions';
import { PartModel } from 'src/app/modules/shared/models/part.model';
import { AccountModel } from 'src/app/modules/shared/models/account.model';
import { SessionService } from 'src/app/services/session.service';
import { DiffMatchPatch } from 'node_modules/ng-diff-match-patch';
import { Router } from '@angular/router';

@Component({
  selector: 'app-project-copy',
  templateUrl: './project-copy.component.html',
  styleUrls: ['./project-copy.component.css']
})
export class ProjectCopyComponent implements OnInit {

  @ViewChild('copyDiv')
  public copyDiv: ElementRef
  @ViewChild('editingDiv')
  public editingDiv: ElementRef
  @ViewChild('diff')
  public diff: ElementRef;

  public viewingCopy: Observable<CopyModel>;

  public viewingProject: Observable<ProjectModel>;

  public writingPart: Observable<PartModel>;

  public editErrorMessage: Observable<string>;

  public submit = false;

  constructor(private store: Store<AppState>,
    private router: Router,
    public sessionService: SessionService) {
  }

  ngOnInit() {
    this.viewingCopy = this.store.select('projects', 'viewingCopy').pipe(
      map((copy: CopyModel) => {
        return copy;
      }))
    this.writingPart = this.store.select('projects', 'writingPart').pipe(
      map((writingPart: PartModel) => {
        return writingPart;
      })
    )
    this.viewingProject = this.store.select('projects', 'viewingProject').pipe(
      map((viewingProject: ProjectModel) =>{
        return viewingProject;
      })
    )
    this.editErrorMessage = this.store.select('projects', 'editErrorMessage');
  }

  submitText() {
    this.submit = true;
    const text = this.editingDiv.nativeElement.innerText;
    let sub = this.writingPart.subscribe(
      (part: PartModel) => {
        if (this.submit) {
          this.store.dispatch(new ProjectActions.PostPartValueAndReviewStatusOnPartId({ partId: part.idField, value: text, reviewStatus: "LOCKED" }))
        }
        this.submit = false;
      }
    )
    sub.unsubscribe();
  }

  releasePart() {
    let sub = this.writingPart.subscribe(
      (part: PartModel) => {
          this.store.dispatch(new ProjectActions.DeletePart(part.idField))
      }
    )
    sub.unsubscribe();
  }

  editingMode = false;
  preText = '';


  onEdit($event) {
    if (!this.editingMode) {
      this.editingMode = true;
      this.preText = this.copyDiv.nativeElement.innerText;
    }
  }

  onEditSubmit() {
    const postText = this.copyDiv.nativeElement.innerText;
    let sub = this.viewingCopy.subscribe(
      (viewingCopy: CopyModel) => {
        let diffMatch = new DiffMatchPatch()
        // @ts-ignore
        const patch = diffMatch.patch_make(viewingCopy.value, postText);
        const patchToString = diffMatch.patch_toText(patch);
        this.copyDiv.nativeElement.innerText = viewingCopy.value
        this.store.dispatch(new ProjectActions.SubmitEdit({copyId: viewingCopy.idField, deltaString: patchToString}))
      }
    )
    sub.unsubscribe();
    this.editingMode = false;

  }

  onClearSubmit() {
    this.editingMode = false;
    this.copyDiv.nativeElement.innerText = this.preText;
  }

  navigateToUser(username: string) {
    this.router.navigate(['accounts' + username]);
  }
}