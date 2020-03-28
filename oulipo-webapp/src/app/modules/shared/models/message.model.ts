export class MessageModel {
    constructor(
        public toUsername: string, 
        public toUserId: number,
        public fromUsername: string,
        public fromUserId: number, 
        public sentAt: string, 
        public value: string) {}
}