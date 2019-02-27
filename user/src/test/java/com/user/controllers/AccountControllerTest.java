package com.user.controllers;

import static com.common.models.variables.StandardFields.DEFAULT_PAGE_SIZE;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

import com.common.models.dtos.AccountDto;
import com.common.models.dtos.AccountStatus;
import com.common.models.dtos.ProjectDto;
import org.junit.Test;

public class AccountControllerTest extends AccountTest {

    @Test
    public void createAccountRequest() throws Exception {
        super.createAccountWithUsernameAndEmail(defaultUsername, defaultEmail)
             .andDo(print())
             .andExpect(jsonPath("$.username", is(defaultUsername)))
             .andExpect(jsonPath("$.email", is(defaultEmail)))
             .andExpect(jsonPath("$.status", is(AccountStatus.ACTIVE.toString())));
    }

    @Test
    public void updateAccount() throws Exception {
        String newUsername = "Updated";
        String newEmail = "Changed!@email.net";
        AccountDto accountDto = createDefaultAccount();
        mockMvc.perform(patch(ACCOUNTS_PATH + "/" + accountDto.getIdField()).param("username", newUsername)
                                                                            .param("email", newEmail)
                                                                            .param("hashedPassword", "SomeRandomPass"))
               .andDo(print())
               .andExpect(jsonPath("$.idField", is(accountDto.getIdField())))
               .andExpect(jsonPath("$.username", is(newUsername)))
               .andExpect(jsonPath("$.email", is(newEmail)))
               .andExpect(jsonPath("$.status", is(accountDto.getStatus().toString())))
               .andExpect(jsonPath(selfLink, is(hostname + "accounts/" + accountDto.getIdField())));
    }

    @Test
    public void findById() throws Exception {
        AccountDto accountDto = createDefaultAccount();
        mockMvc.perform(get(ACCOUNTS_PATH + accountDto.getIdField()))
               .andDo(print())
               .andExpect(jsonPath("$.idField", is(accountDto.getIdField())))
               .andExpect(jsonPath("$.username", is(accountDto.getUsername())))
               .andExpect(jsonPath("$.email", is(accountDto.getEmail())))
               .andExpect(jsonPath("$.status", is(accountDto.getStatus().toString())))
               .andExpect(jsonPath(selfLink, is(hostname + "accounts/" + accountDto.getIdField())));

    }

    @Test
    public void findAll() throws Exception {
        mockMvc.perform(get(ACCOUNTS_PATH))
               .andDo(print())
               .andExpect(jsonPath("$.page.size", is(DEFAULT_PAGE_SIZE)))
               .andExpect(jsonPath("$.page.totalElements", is(numOfAccountsCreated)))
               .andExpect(jsonPath("$.page.totalPages", is(numOfAccountsCreated > 0 ? 1 : 0)))
               .andExpect(jsonPath("$.page.number", is(0)));
    }
}