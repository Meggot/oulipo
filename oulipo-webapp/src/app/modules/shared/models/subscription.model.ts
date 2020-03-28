export class SubscriptionModel {
    constructor(public idField: number,
        public notificationType: string,
        public entityId: number,
        public postboxId: number) {}
}