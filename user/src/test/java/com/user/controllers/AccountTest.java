// Copyright (c) 2019 Travelex Ltd

package com.user.controllers;

import com.common.models.dtos.*;
import com.common.models.enums.AccountRelationshipStatus;
import com.common.models.enums.AccountRelationshipType;
import com.common.models.enums.GroupType;
import com.common.models.utils.ReadWriteUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(properties = "spring.profiles.active = Test")
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class AccountTest {

    @Autowired
    public MockMvc mockMvc;

    public String MESSAGES_PATH = "/messages/";

    public String ACCOUNTS_PATH = "/accounts/";

    public String RELATIONSHIPS_PATH = "/relationship/";

    public String GROUP_PATH = "/groups/";

    public String hostname = "http://localhost/";

    public static String selfLink = "$._links.self.href";

    public String defaultUsername = "Teste";

    public String defaultEmail = "TestingEmail@Email.net";

    public String defaultPassword = "hunter1";

    public String defaultGroupName = "Testing Group";

    public String defaultGroupDescription = "This is a Group for Testing.";

    public String defaultGroupType = GroupType.OPEN.toString();

    public static int numOfAccountsCreated = 0;

    public static int numofRelationshipsCreated = 0;

    public static int numOfGroupsCreated = 0;

    @Test
    public void init() {

    }

    public ResultActions createAccountWithUsernameAndEmail(String username, String email) throws Exception {
        numOfAccountsCreated++;

        return mockMvc.perform((post("/api/register"))
                .param("username", username)
                .param("email", email)
                .param("hashedPassword", defaultPassword));
    }

    public GroupDto createDefaultGroup(AccountDto leader) throws Exception {
        numOfGroupsCreated++;
        return ReadWriteUtils.asObjectFromString(GroupDto.class,
                this.mockMvc.perform(post(GROUP_PATH).header("User", leader.getIdField())
                        .param("name", defaultGroupName)
                        .param("description", defaultGroupDescription)
                        .param("type", defaultGroupType)).andReturn()
                        .getResponse().getContentAsString());
    }


    public GroupDto createGroupWithName(AccountDto leader, String groupName) throws Exception {
        numOfGroupsCreated++;
        return ReadWriteUtils.asObjectFromString(GroupDto.class,
                this.mockMvc.perform(post(GROUP_PATH).header("User", leader.getIdField())
                        .param("name", groupName)
                        .param("description", defaultGroupDescription)
                        .param("type", defaultGroupType)).andReturn()
                        .getResponse().getContentAsString());
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
                mockMvc.perform(post(ACCOUNTS_PATH + added.getUsername() + "/relationship")
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
