// Copyright (c) 2019 Travelex Ltd

package com.user.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.common.models.dtos.AccountDto;
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

@EnableKafka
@SpringBootTest(properties = "spring.profiles.active = Test")
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class AccountTest {

    @Autowired
    public MockMvc mockMvc;

    public String ACCOUNTS_PATH = "/accounts/";

    public String hostname = "http://localhost/";

    public static String selfLink = "$._links.self.href";

    public String defaultUsername = "Testing";

    public String defaultEmail = "TestingEmail@Email.net";

    public String defaultPassword = "hunter1";

    public static int numOfAccountsCreated = 0;

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
                                                 createAccountWithUsernameAndEmail(defaultUsername, defaultEmail).andReturn()
                                                                                                                 .getResponse()
                                                                                                                 .getContentAsString());
    }

}
