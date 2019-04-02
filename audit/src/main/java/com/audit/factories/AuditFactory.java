// Copyright (c) 2019 Travelex Ltd

package com.audit.factories;

import com.audit.dao.entites.Audit;
import com.common.models.messages.*;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AuditFactory {

    public Audit toAudit(AccountUpdateMessage accountUpdateMessage) {
        Audit audit = new Audit();
        audit.setEntityId(accountUpdateMessage.getAccountId());
        audit.setOriginUserId(accountUpdateMessage.getAccountId());
        audit.setEventType(MessageType.ACCOUNT_UPDATE);
        StringBuilder auditValueBuilder = new StringBuilder();
        auditValueBuilder.append("Username oval: ").append(accountUpdateMessage.getOldUsername());
        auditValueBuilder.append(" nval: ").append(accountUpdateMessage.getNewUsername());
        auditValueBuilder.append(", email oval: ").append(accountUpdateMessage.getOldEmail());
        auditValueBuilder.append(" nval: ").append(accountUpdateMessage.getNewEmail());
        audit.setValue(auditValueBuilder.toString());
        return audit;
    }

    public static Audit toAudit(AccountCreationMessage message) {
        Audit audit = new Audit();
        audit.setEntityId(message.getAccountId());
        audit.setOriginUserId(message.getAccountId());
        audit.setEventType(MessageType.ACCOUNT_CREATION);
        StringBuilder auditValueBuilder = new StringBuilder();
        auditValueBuilder.append("Username nval: ").append(message.getUsername());
        auditValueBuilder.append("Email nval: ").append(message.getEmail());
        audit.setValue(auditValueBuilder.toString());
        return audit;
    }

    public static Audit toAudit(ProjectCreationMessage message) {
        Audit audit = new Audit();
        audit.setEntityId(Integer.parseInt(message.getProjectId()));
        audit.setOriginUserId(Integer.parseInt(message.getUserId()));
        audit.setEventType(MessageType.PROJECT_CREATION);
        StringBuilder auditValueBuilder = new StringBuilder();
        auditValueBuilder.append("Title nval: ").append(message.getTitle());
        auditValueBuilder.append("Synopsis nval: ").append(message.getSynopsis());
        audit.setValue(auditValueBuilder.toString());
        return audit;
    }


    public static Audit toAudit(ProjectUpdateMessage message) {
        Audit audit = new Audit();
        audit.setEntityId(Integer.parseInt(message.getProjectId()));
        audit.setOriginUserId(Integer.parseInt(message.getUserId()));
        audit.setEventType(MessageType.PROJECT_UPDATE);
        StringBuilder auditValueBuilder = new StringBuilder();
        auditValueBuilder.append("Title[ oval: ").append(message.getOldTitle());
        auditValueBuilder.append("  nval: ").append(message.getNewTitle());
        auditValueBuilder.append("] Synopsis[ oval: ").append(message.getOldSynopsis());
        auditValueBuilder.append("  nval: ").append(message.getNewSynopsis());
        auditValueBuilder.append("]");
        audit.setValue(auditValueBuilder.toString());
        return audit;
    }

    public static Audit toAudit(ProjectTagCreationMessage message) {
        Audit audit = new Audit();
        audit.setEntityId(message.getTagId());
        audit.setOriginUserId(message.getUserIdAdded());
        audit.setEventType(MessageType.PROJECT_TAG_CREATION);
        StringBuilder auditValueBuilder = new StringBuilder();
        auditValueBuilder.append(message.getProjectTitle() + "- Type: " + message.getType() + ":" + message.getValue());
        audit.setValue(auditValueBuilder.toString());
        return audit;
    }

    public static Audit toAudit(ProjectTagUpdateMessage message) {
        Audit audit = new Audit();
        audit.setEntityId(message.getTagId());
        audit.setOriginUserId(message.getUserIdAdded());
        audit.setEventType(MessageType.PROJECT_TAG_UPDATE);
        audit.setValue(message.getProjectTitle() + "- Type: " + message.getType() + ": " + message.getValue());
        return audit;
    }

    public static Audit toAudit(ProjectPartUpdateMessage message) {
        Audit audit = new Audit();
        audit.setEventType(MessageType.PROJECT_PART_UPDATE);
        audit.setOriginUserId(Integer.parseInt(message.getPartUserId()));
        audit.setEntityId(message.getPartId());
        audit.setValue(message.getPartAuthorName() + " updated a part on project " + message.getProjectId() + " of value " + message.getPartValue());
        return audit;
    }

    public static Audit toAudit(ProjectPartCreationMessage message) {
        Audit audit = new Audit();
        audit.setEntityId(message.getPartId());
        audit.setOriginUserId(Integer.parseInt(message.getPartUserId()));
        audit.setEventType(MessageType.PROJECT_PART_CREATION);
        audit.setValue(message.getPartAuthorName() + " creatd a part on project " + message.getProjectId());
        return audit;
    }
}
