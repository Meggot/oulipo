package com.user.controllers;

import com.common.models.dtos.AccountDto;
import com.common.models.dtos.AccountRelationshipDto;
import com.common.models.dtos.AccountRelationshipStatus;
import com.common.models.dtos.AccountRelationshipType;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class AccountRelationshipControllerTest extends AccountTest {

    @Test
    public void findAllRelationships() throws Exception {
        AccountDto added = createDefaultAccount();
        AccountDto addedBy = createDefaultAccount();
        createRelationshipDto(added, addedBy);
        this.mockMvc.perform(get(RELATIONSHIPS_PATH).header("User", added.getIdField()))
                .andDo(print())
                .andExpect(jsonPath("$._embedded.accountRelationshipDtoList", iterableWithSize(numofRelationshipsCreated)))
                .andExpect(jsonPath(selfLink, is(hostname + "relationship?page=0&size=20")))
                .andExpect(jsonPath("$.page.size", is(20)))
                .andExpect(jsonPath("$.page.totalElements", is(numofRelationshipsCreated)))
                .andExpect(jsonPath("$.page.totalPages", is(1)))
                .andExpect(jsonPath("$.page.number", is(0)));
    }

    @Test
    public void getById() throws Exception {
        AccountDto added = createDefaultAccount();
        AccountDto addedBy = createDefaultAccount();
        AccountRelationshipDto accountRelationshipDto = createRelationshipDto(added, addedBy);
        this.mockMvc.perform(get(RELATIONSHIPS_PATH + accountRelationshipDto.getIdField()))
                .andDo(print())
                .andExpect(jsonPath("$.idField", is(accountRelationshipDto.getIdField())))
                .andExpect(jsonPath("$.addedUsername", is(added.getUsername())))
                .andExpect(jsonPath("$.addedUserId", is(added.getIdField())))
                .andExpect(jsonPath("$.addedByUsername", is(addedBy.getUsername())))
                .andExpect(jsonPath("$.addedByUserId", is(addedBy.getIdField())))
                .andExpect(jsonPath("$.type", is(AccountRelationshipType.FRIEND.toString())))
                .andExpect(jsonPath("$.status", is(AccountRelationshipStatus.REQUESTED.toString())))
                .andExpect(jsonPath(selfLink, is(hostname + "relationship/" + accountRelationshipDto.getIdField())));
    }

    @Test
    public void postRequest() throws Exception {
        AccountDto added = createDefaultAccount();
        AccountDto addedBy = createDefaultAccount();
        numofRelationshipsCreated++;
        this.mockMvc.perform(post(ACCOUNTS_PATH + added.getUsername() + "/relationship")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("type", AccountRelationshipType.FRIEND.toString())
                .header("User", addedBy.getIdField()))
                .andDo(print())
                .andExpect(jsonPath("$.addedByUsername", is(addedBy.getUsername())))
                .andExpect(jsonPath("$.addedByUserId", is(addedBy.getIdField())))
                .andExpect(jsonPath("$.addedUsername", is(added.getUsername())))
                .andExpect(jsonPath("$.addedUserId", is(added.getIdField())))
                .andExpect(jsonPath("$.type", is(AccountRelationshipType.FRIEND.toString())))
                .andExpect(jsonPath("$.status", is(AccountRelationshipStatus.REQUESTED.toString())));
    }
}