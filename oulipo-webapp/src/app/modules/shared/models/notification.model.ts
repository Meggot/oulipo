export class NotificationMailModel {
    constructor(public idField: number,
        public notification: {
            idField: number,
            entityId: number,
            type: string,
            message: string
        },
        public recieved: string,
        public status: string) {}
}
