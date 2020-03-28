import { AccountGroupMembership } from './account-group-membership.model';
import { ProjectGroupMembership } from './project-group-membership.model';

export class GroupModel {

    constructor(public name: string,
        public idField: number,
        public description: string,
        public type: string,
        public members: AccountGroupMembership[],
        public projects: ProjectGroupMembership[]) {}
}