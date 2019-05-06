package com.notify.controllers;


import com.common.models.dtos.NotificationStatus;
import com.common.models.dtos.NotificationType;
import com.common.models.dtos.PostBoxDto;
import com.common.models.dtos.PostFlagStatus;
import com.common.models.utils.ReadWriteUtils;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.common.models.dtos.NotificationType.PROJECT_PART_POSTED;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PostboxControllerTest extends PostboxTest{

    @Test
    public void getMyPost() throws Exception {
        Integer posterUserId = 21312;
        PostBoxDto postBoxDto = createPostBoxOnUserId(posterUserId);
        this.mockMvc.perform(get(POSTBOX_PATH)
                .header("User", posterUserId))
                .andDo(print())
                .andExpect(jsonPath("$.idField", is(postBoxDto.getIdField())))
                .andExpect(jsonPath("$.address", is(postBoxDto.getAddress())))
                .andExpect(jsonPath("$.flagStatus", is(postBoxDto.getFlagStatus().toString())))
                .andExpect(jsonPath("$.userId", is(posterUserId)))
                .andExpect(jsonPath("$.unreadMail", iterableWithSize(0)))
                .andExpect(jsonPath("$.subscriptionDtos", iterableWithSize(0)));
        this.mockMvc.perform(get(POSTBOX_PATH + postBoxDto.getIdField())
                .header("User", posterUserId))
                .andDo(print())
                .andExpect(jsonPath("$.idField", is(postBoxDto.getIdField())))
                .andExpect(jsonPath("$.address", is(postBoxDto.getAddress())))
                .andExpect(jsonPath("$.flagStatus", is(postBoxDto.getFlagStatus().toString())))
                .andExpect(jsonPath("$.userId", is(posterUserId)))
                .andExpect(jsonPath("$.unreadMail", iterableWithSize(0)))
                .andExpect(jsonPath("$.subscriptionDtos", iterableWithSize(0)));
    }

    @Test
    public void subscribeOnPostBox() throws Exception {
        Integer entityId = 5;
        PostBoxDto postBoxDto = createPostBoxOnUserId(numOfPostboxesCreated);
        List<NotificationType> entityTypes = Lists.newArrayList(PROJECT_PART_POSTED);
        numOfSubscriptionsCreated++;
        this.mockMvc.perform(post(POSTBOX_PATH + postBoxDto.getIdField() + "/subscribe")
                .header("User", defaultUserId)
                .param("entityId", String.valueOf(entityId))
                .param("types", ReadWriteUtils.toCsv(new ArrayList<>(entityTypes))))
                .andExpect(jsonPath("$._embedded.subscriptionDtoList[0].notificationType", is(PROJECT_PART_POSTED.toString())))
                .andExpect(jsonPath("$._embedded.subscriptionDtoList[0].entityId", is(entityId)))
                .andExpect(jsonPath("$._embedded.subscriptionDtoList[0].postboxId",is (postBoxDto.getIdField())))
                .andDo(print());
    }

    @Test
    public void testNotificationReceivedAndRead() throws Exception {
        Integer newPostBox = 123;
        PostBoxDto postBoxDto = createPostBoxOnUserId(newPostBox);
        this.mockMvc.perform(get(POSTBOX_PATH)
                .header("User", newPostBox))
                .andExpect(jsonPath("$.idField", is(postBoxDto.getIdField())))
                .andExpect(jsonPath("$.address", is(postBoxDto.getAddress())))
                .andExpect(jsonPath("$.flagStatus", is(postBoxDto.getFlagStatus().toString())))
                .andExpect(jsonPath("$.userId", is(newPostBox)))
                .andExpect(jsonPath("$.unreadMail", iterableWithSize(0)))
                .andExpect(jsonPath("$.subscriptionDtos", iterableWithSize(0)));
        EmbeddedList subscriptionDto = createSubscription(postBoxDto.getIdField(),defaultEntityId, defaultNotificationTypes);
        this.mockMvc.perform(get(POSTBOX_PATH)
                .header("User", newPostBox))
                .andDo(print())
                .andExpect(jsonPath("$.idField", is(postBoxDto.getIdField())))
                .andExpect(jsonPath("$.address", is(postBoxDto.getAddress())))
                .andExpect(jsonPath("$.flagStatus", is(postBoxDto.getFlagStatus().toString())))
                .andExpect(jsonPath("$.userId", is(newPostBox)))
                .andExpect(jsonPath("$.unreadMail", iterableWithSize(0)))
                .andExpect(jsonPath("$.subscriptionDtos", iterableWithSize(1)))
                .andExpect(jsonPath("$.subscriptionDtos[0].notificationType", is(defaultNotificationType.toString())))
                .andExpect(jsonPath("$.subscriptionDtos[0].idField", is(subscriptionDto._embedded.subscriptionDtoList.get(0).getIdField())))
                .andExpect(jsonPath("$.subscriptionDtos[0].entityId", is(defaultEntityId)))
                .andExpect(jsonPath("$.subscriptionDtos[0].postboxId", is(postBoxDto.getIdField())));
        createNotificationOnEntityIdAndType(defaultEntityId, defaultNotificationType);
        this.mockMvc.perform(get(POSTBOX_PATH)
                .header("User", newPostBox))
                .andDo(print())
                .andExpect(jsonPath("$.idField", is(postBoxDto.getIdField())))
                .andExpect(jsonPath("$.address", is(postBoxDto.getAddress())))
                .andExpect(jsonPath("$.flagStatus", is(PostFlagStatus.UNREAD.toString())))
                .andExpect(jsonPath("$.userId", is(newPostBox)))
                .andExpect(jsonPath("$.unreadMail", iterableWithSize(1)))
                .andExpect(jsonPath("$.unreadMail[0].notification.entityId",is(defaultEntityId)))
                .andExpect(jsonPath("$.unreadMail[0].notification.type",is(defaultNotificationType.toString())))
                .andExpect(jsonPath("$.unreadMail[0].notification.message",is(defaultMessageMessage)))
                .andExpect(jsonPath("$.unreadMail[0].status",is(NotificationStatus.UNREAD.toString())))
                .andExpect(jsonPath("$.subscriptionDtos", iterableWithSize(1)))
                .andExpect(jsonPath("$.subscriptionDtos[0].notificationType", is(defaultNotificationType.toString())))
                .andExpect(jsonPath("$.subscriptionDtos[0].entityId", is(defaultEntityId)))
                .andExpect(jsonPath("$.subscriptionDtos[0].postboxId", is(postBoxDto.getIdField())))
                .andDo(print());
        this.mockMvc.perform(get(POSTBOX_PATH)
                .header("User", newPostBox))
                .andDo(print())
                .andExpect(jsonPath("$.idField", is(postBoxDto.getIdField())))
                .andExpect(jsonPath("$.address", is(postBoxDto.getAddress())))
                .andExpect(jsonPath("$.flagStatus", is(PostFlagStatus.READ.toString())))
                .andExpect(jsonPath("$.userId", is(newPostBox)))
                .andExpect(jsonPath("$.unreadMail", iterableWithSize(0)))
                .andExpect(jsonPath("$.subscriptionDtos", iterableWithSize(1)))
                .andExpect(jsonPath("$.subscriptionDtos[0].notificationType", is(defaultNotificationType.toString())))
                .andExpect(jsonPath("$.subscriptionDtos[0].idField", is(subscriptionDto._embedded.subscriptionDtoList.get(0).getIdField())))
                .andExpect(jsonPath("$.subscriptionDtos[0].entityId", is(defaultEntityId)))
                .andExpect(jsonPath("$.subscriptionDtos[0].postboxId", is(postBoxDto.getIdField())));
    }

}