import { MessageModel } from './message.model';
import { AccountRelationshipModel } from './account-relationship.model';

export class AccountModel {
  constructor(public idField: number, public username: string, public email: string, public status: string, 
    public sentMessages?: MessageModel[],
    public receivedMessages?: MessageModel[],
    public logins?: {inetAddress: string, loginTime: string}[],
    public groups?: {groupName: string, groupId: number, role: string}[],
    public relationships?: AccountRelationshipModel[]
    ) {
  }
}
