export class CreateProjectModel {
    constructor(public title: string,
        public synopsis: string,
        public visibilityType: string,
        public projectType: string,
        public sourcingType: string) {}
}