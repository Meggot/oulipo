export class AccountGroupMembership{

    constructor(public idField: number,
        public accountUsername: string,
        public accountId: number,
        public addedByUsername: string,
        public addedById: number,
        public groupId: number,
        public role: string,
        public groupName: string,
        public added: string) {}
}