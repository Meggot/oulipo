package com.common.models.messages;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Message<T> {

    T body;

    Integer entityId;

    MessageType type;

}
