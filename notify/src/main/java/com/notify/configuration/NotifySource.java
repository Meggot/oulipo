package com.notify.configuration;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.SubscribableChannel;

@Profile( "!Test")
public interface NotifySource {

    @Input("account-update")
    SubscribableChannel accountUpdateInput();

    @Input("message-sent")
    SubscribableChannel messageReceivedInput();

    @Input("account-creation")
    SubscribableChannel accountCreationInput();

    @Input("project-creation")
    SubscribableChannel projectCreationOutput();

    @Input("project-update")
    SubscribableChannel projectUpdateOutput();

    @Input("project-selected")
    SubscribableChannel projectSelectedOutput();

    @Input("project-tag-creation")
    SubscribableChannel projectTagCreation();

    @Input("project-tag-update")
    SubscribableChannel projectTagUpdate();

    @Input("project-role-creation")
    SubscribableChannel projectRoleCreation();

    @Input("project-role-update")
    SubscribableChannel projectRoleUpdate();

    @Input("copy-part-creation")
    SubscribableChannel copyPartCreation();

    @Input("copy-part-update")
    SubscribableChannel copyPartUpdate();

    @Input("copy-edit-creation")
    SubscribableChannel copyEditCreation();

    @Input("copy-edit-update")
    SubscribableChannel copyEditUpdate();

}
