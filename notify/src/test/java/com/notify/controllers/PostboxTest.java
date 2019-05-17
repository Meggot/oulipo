package com.notify.controllers;

import com.common.models.dtos.MessageDto;
import com.common.models.dtos.NotificationType;
import com.common.models.dtos.PostBoxDto;
import com.common.models.dtos.ProjectPartDto;
import com.common.models.messages.Message;
import com.common.models.messages.MessageSentMessage;
import com.common.models.messages.MessageType;
import com.common.models.messages.ProjectPartCreationMessage;
import com.common.models.utils.ReadWriteUtils;
import com.google.common.collect.Lists;
import com.notify.streaming.AccountLifecycleStreamer;
import com.notify.streaming.CopyLifecycleStreamer;
import com.notify.streaming.InMemoryNotifyStreamer;
import com.notify.streaming.ProjectLifecycleStreamer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest(properties = "spring.profiles.active = Test")
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class PostboxTest {

    @Autowired
    public MockMvc mockMvc;

    public String POSTBOX_PATH = "/postbox/";

    public String hostname = "http://localhost/";

    public static String selfLink = "$._links.self.href";

    public static int defaultUserId = 1;

    public static int defaultPostBoxId;

    public static boolean hasDefaultPostboxBeenCreated;

    public static int numOfPostboxesCreated = 0;

    public static int numOfSubscriptionsCreated = 0;

    public static int numOfNotificationsCreated = 0;

    public static int numOfNotificationMailsCreated = 0;

    public static List<NotificationType> defaultNotificationTypes = Lists.newArrayList(NotificationType.INBOX_MESSAGE_RECEIVED);

    public static NotificationType defaultNotificationType = NotificationType.INBOX_MESSAGE_RECEIVED;

    public static int defaultEntityId = 142;

    public static String defaultMessageSenderName = "MrTalkative";

    public static String defaultMessageMessage = defaultMessageSenderName + ": " + "Hey there how is the weather I hear its awesome";

    @Autowired
    private InMemoryNotifyStreamer inMemoryNotifyStreamer;

    @Test

    public void testInstance() {
        assertThat(true).isTrue();
    }
    @Before
    public void setup() throws Exception {
        if (!hasDefaultPostboxBeenCreated) {
            this.defaultPostBoxId = getDefaultPostbox().getIdField();
        }
    }

    public EmbeddedList createDefaultSubscription() throws Exception {
        return createSubscription(defaultPostBoxId, defaultEntityId, defaultNotificationTypes);
    }

    public PostBoxDto getDefaultPostbox() throws Exception {
        return createPostBoxOnUserId(defaultUserId);
    }

    public void createDefaultNotification() {
        createNotificationOnEntityIdAndType(defaultEntityId, defaultNotificationType);
    }

    public void createNotificationOnEntityIdAndType(Integer entityId, NotificationType notificationType) {
        numOfNotificationsCreated++;

        switch(notificationType) {
            case PROJECT_PART_POSTED:
                Message<ProjectPartDto> projectPartPosted = new Message<>(new ProjectPartDto(),
                        entityId,
                        MessageType.PROJECT_PART_CREATION);
                this.inMemoryNotifyStreamer.handleProjectPartCreation(projectPartPosted);
                return;
            case INBOX_MESSAGE_RECEIVED:
                MessageDto message = new MessageDto();
                message.setValue(defaultMessageMessage);
                message.setFromUsername(defaultMessageSenderName);
                Message<MessageDto> messageReceived = new Message<>(message,
                        entityId,
                        MessageType.PROJECT_PART_CREATION);
                this.inMemoryNotifyStreamer.handleAccountMessage(messageReceived);
        }
    }

    public PostBoxDto createPostBoxOnUserId(Integer userId) throws Exception {
        numOfPostboxesCreated++;
        return ReadWriteUtils.asObjectFromString(PostBoxDto.class,
                this.mockMvc.perform(get(POSTBOX_PATH)
                        .header("User", userId)).andReturn()
                        .getResponse().getContentAsString());
    }


    public EmbeddedList createSubscription(Integer postboxId, Integer entityId, List<NotificationType> types) throws Exception {
        numOfSubscriptionsCreated++;

        String typesInCsv = ReadWriteUtils.toCsv(new ArrayList<>(types));

        return ReadWriteUtils.asObjectFromString(EmbeddedList.class,
                this.mockMvc.perform(post(POSTBOX_PATH + postboxId + "/subscribe").header("User", defaultUserId)
                        .param("entityId", String.valueOf(entityId))
                        .param("types", typesInCsv)).andReturn().getResponse().getContentAsString());
    }
}
