package com.notify.streaming;

import com.common.models.messages.*;

public interface ProjectLifecycleStreamer {

    void listen(ProjectTagCreationMessage message, int partition);
    void listen(ProjectTagUpdateMessage message, int partition);
    void listen(ProjectPartUpdateMessage message, int partition);
    void listen(ProjectUpdateMessage message, int partition);
    void listen(ProjectPartCreationMessage message, int partition);

}
