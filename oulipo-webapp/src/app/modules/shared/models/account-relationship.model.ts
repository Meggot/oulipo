export class AccountRelationshipModel {
    constructor(private idField: number,
        private addedByUsername: string,
        private addedByUserId: number,
        private addedUsername: string,
        private addedUserId: number,
        private type: string,
        private status: string) {}
}