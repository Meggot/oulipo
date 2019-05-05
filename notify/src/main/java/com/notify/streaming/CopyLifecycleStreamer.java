package com.notify.streaming;

import com.common.models.messages.CopyEditCreationMessage;
import com.common.models.messages.CopyEditUpdateMesage;

public interface CopyLifecycleStreamer {

    void listen(CopyEditUpdateMesage message, int partition);
    void listen(CopyEditCreationMessage message, int partition);

}
