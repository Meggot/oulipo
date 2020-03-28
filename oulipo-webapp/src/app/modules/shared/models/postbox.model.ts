import { NotificationMailModel } from './notification.model';
import { SubscriptionModel } from './subscription.model';

export class PostboxModel {
    constructor(public idField: number,
        public address: string,
        public flagStatus: string,
        public userId: number,
        public lastOpened: string,
        public unreadMail: NotificationMailModel[],
        public subscriptionDtos: SubscriptionModel[],
        public online: boolean) {}
}