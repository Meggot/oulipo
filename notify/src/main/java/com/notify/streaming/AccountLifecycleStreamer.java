package com.notify.streaming;

import com.common.models.messages.MessageSentMessage;

public interface AccountLifecycleStreamer {

    void listen(MessageSentMessage message, int partition);
}
