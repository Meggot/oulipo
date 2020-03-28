export class PartModel {
  constructor(
    public idField: number, 
    public projectId: number,
     public projectTitle: string, 
     public sequence: number, 
     public status: string, 
     public authorName: string, 
     public activeValue: string, 
     public authorUserId: number,
     public added: string) {}
}
