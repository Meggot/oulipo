package com.project.configuration;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

@Profile( "!Test")
public interface ProjectSource {

    @Input("account-update")
    SubscribableChannel accountUpdateInput();

    @Input("account-creation")
    SubscribableChannel accountCreationInput();

    @Input("system-add-tag")
    SubscribableChannel systemAddTagInput();

    @Output("project-creation")
    MessageChannel projectCreationOutput();

    @Output("project-update")
    MessageChannel projectUpdateOutput();

    @Output("project-selected")
    MessageChannel projectSelectedOutput();

    @Output("project-tag-creation")
    MessageChannel projectTagCreation();

    @Output("project-tag-update")
    MessageChannel projectTagUpdate();

    @Output("project-role-creation")
    MessageChannel projectRoleCreation();

    @Output("project-role-update")
    MessageChannel projectRoleUpdate();

    @Output("copy-part-creation")
    MessageChannel copyPartCreation();

    @Output("copy-part-update")
    MessageChannel copyPartUpdate();

    @Output("copy-edit-creation")
    MessageChannel copyEditCreation();

    @Output("copy-edit-update")
    MessageChannel copyEditUpdate();

}
