package com.notify.streaming;

import com.common.models.dtos.MessageDto;
import com.common.models.messages.Message;

public interface AccountLifecycleStreamer {

    void handleAccountMessage(Message<MessageDto> accountMessage);
}
