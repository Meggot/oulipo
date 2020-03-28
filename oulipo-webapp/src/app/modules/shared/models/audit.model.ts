export class AuditModel {
  constructor(
    public idField: number,
    public originUserId: number,
    public timeStamp: string,
    public entityId: string, 
    public value: string, 
    public eventType: string) {}
}



/**
 * 
    private Integer idField;

    private Integer entityId;

    private MessageType eventType;

    private Integer originUserId;

    private String value;

    private LocalDateTime timeStamp;
 */