export class ProjectGroupMembership {
    constructor(public idField: number,
        public projectId: number,
        public projectName: string,
        public groupName: string,
        public groupId: number,
        public addedByUsername: string,
        public addedById: number,
        public added: string) {}
}