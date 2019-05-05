package com.common.models.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageSentMessage {
    private String toUsername;

    private Integer toUserId;

    private String fromUsername;

    private Integer fromUserId;

    private String sentTime;

    private String value;
}
