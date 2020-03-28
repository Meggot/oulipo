
export class EditModel {
    constructor(public idField: number,
        public delta: string,
        public authorName: string,
        public projectTitle: string,
        public copyId: number,
        public projectId: number,
        public status: string) {}
}