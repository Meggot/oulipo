import { ProjectModel } from './project.model';
import { PartModel } from './part.model';

export class AuthorModel {
    constructor(public idField: number,
        public userId: number,
        public username: string,
        public createdProjects: ProjectModel[],
        public createdParts: PartModel[]) {}
}