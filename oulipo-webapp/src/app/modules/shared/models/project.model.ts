import {CopyModel} from './copy.model';
import {PartModel} from './part.model';
import {ProjectRolesModel} from './project-roles.model';
import {ProjectTagModel} from './project-tag-model.model';

export class ProjectModel {
  constructor(public projectId: number,
              public title: string,
              public synopsis: string,
              public visibilityType: string,
              public type: string,
              public sourcingType: string,
              public copy: CopyModel,
              public parts: PartModel[],
              public roles: ProjectRolesModel[],
              public tags: ProjectTagModel[],
              public originalAuthor: string) {
  }
}
