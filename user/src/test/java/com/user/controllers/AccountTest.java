// Copyright (c) 2019 Travelex Ltd

package com.user.controllers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.common.models.dtos.AccountDto;
import com.common.models.dtos.AccountRelationshipDto;
import com.common.models.dtos.AccountRelationshipStatus;
import com.common.models.dtos.AccountRelationshipType;
import com.common.models.utils.ReadWriteUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest(properties = "spring.profiles.active = Test")
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class AccountTest {

    @Autowired
    public MockMvc mockMvc;

    public String MESSAGES_PATH = "/messages/";

    public String ACCOUNTS_PATH = "/accounts/";

    public String RELATIONSHIPS_PATH = "/relationship/";

    public String hostname = "http://localhost/";

    public static String selfLink = "$._links.self.href";

    public String defaultUsername = "Testing";

    public String defaultEmail = "TestingEmail@Email.net";

    public String defaultPassword = "hunter1";

    public static int numOfAccountsCreated = 0;

    public static int numofRelationshipsCreated = 0;

    @Test
    public void init() {

    }

    public ResultActions createAccountWithUsernameAndEmail(String username, String email) throws Exception {
        numOfAccountsCreated++;

        return mockMvc.perform((post(ACCOUNTS_PATH)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("username", username)
                .param("email", email)
                .param("hashedPassword", defaultPassword));
    }

    public AccountDto createDefaultAccount() throws Exception {
        //We have to add the num of accounts to these fields to stop duplciated error.
        defaultUsername = defaultUsername + numOfAccountsCreated;
        defaultEmail = defaultEmail + numOfAccountsCreated;
        return ReadWriteUtils.asObjectFromString(AccountDto.class,
                createAccountWithUsernameAndEmail(defaultUsername, defaultEmail).andDo(print()).andReturn()
                        .getResponse()
                        .getContentAsString());
    }

    public AccountRelationshipDto createRelationshipDto(AccountDto added, AccountDto addedTo) throws Exception {
        numofRelationshipsCreated++;
        return ReadWriteUtils.asObjectFromString(AccountRelationshipDto.class,
                mockMvc.perform(post(ACCOUNTS_PATH + added.getIdField() + "/relationship")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("type", AccountRelationshipType.FRIEND.toString())
                        .header("User", addedTo.getIdField()))
                        .andDo(print())
                        .andExpect(jsonPath("$.addedByUsername", is(addedTo.getUsername())))
                        .andExpect(jsonPath("$.addedUsername", is(added.getUsername())))
                        .andExpect(jsonPath("$.type", is(AccountRelationshipType.FRIEND.toString())))
                        .andExpect(jsonPath("$.status", is(AccountRelationshipStatus.REQUESTED.toString())))
                        .andReturn().getResponse().getContentAsString());
    }

}
