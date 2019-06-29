package com.user.configuration;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.MessageChannel;

@Profile( "!Test")
public interface UserSource {

    @Output("account-update")
    MessageChannel accountUpdateOutput();

    @Output("account-creation")
    MessageChannel accountCreationOutput();

    @Output("message-sent")
    MessageChannel messageSentOutput();

}
