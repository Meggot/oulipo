package com.common.models.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageSentMessage {
    private String toUsername;

    private String fromUsername;

    private String sentTime;

    private String value;
}
