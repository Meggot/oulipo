package com.user.controllers;

import com.common.models.dtos.AccountDto;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class AccountMessageTest extends AccountTest {

    @Test
    public void testSendMessageToUsername() throws Exception {
        String messageValue = "Hello World";
        AccountDto messageTo = createDefaultAccount();
        AccountDto messageFrom = createDefaultAccount();
        this.mockMvc.perform(post(MESSAGES_PATH + "/user/" + messageTo.getUsername() + "/message")
                .param("value", messageValue)
                .header("User", messageFrom.getIdField()))
                .andDo(print())
                .andExpect(jsonPath("$.value", is(messageValue)))
                .andExpect(jsonPath("$.fromUsername", is(messageFrom.getUsername())))
                .andExpect(jsonPath("$.fromUserId", is(messageFrom.getIdField())))
                .andExpect(jsonPath("$.toUsername", is(messageTo.getUsername())));
    }
}
