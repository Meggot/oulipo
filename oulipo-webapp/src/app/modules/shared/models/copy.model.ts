import { EditModel } from './edit.model';

export class CopyModel {
  constructor(public idField: number,
    public projectId: number,
    public value: string,
    public edits: EditModel[]) {}
}