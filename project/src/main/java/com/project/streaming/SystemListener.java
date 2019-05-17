package com.project.streaming;

import com.common.models.messages.Message;
import com.common.models.requests.CreateTagRequest;

public interface SystemListener {

    public void handleSystemAddTag(Message<CreateTagRequest> addTagRequest);
}
