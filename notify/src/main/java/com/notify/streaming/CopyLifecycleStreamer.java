package com.notify.streaming;

import com.common.models.dtos.CopyEditDto;
import com.common.models.messages.CopyEditCreationMessage;
import com.common.models.messages.CopyEditUpdateMesage;
import com.common.models.messages.Message;

public interface CopyLifecycleStreamer {

    void handleCopyEditCreation(Message<CopyEditDto> message);
    void handleCopyEditUpdate(Message<CopyEditDto> message);

}
