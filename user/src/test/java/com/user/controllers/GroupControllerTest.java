package com.user.controllers;

import com.common.models.dtos.AccountDto;
import com.common.models.dtos.GroupDto;
import com.common.models.enums.GroupRole;
import com.common.models.enums.GroupType;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class GroupControllerTest extends AccountTest {

    @Test
    public void createGroup() throws Exception {
        String groupName = "New Group";
        String groupDescription = "This is a new group";
        GroupType groupType = GroupType.OPEN;
        AccountDto accountDto = createDefaultAccount();
        this.mockMvc.perform(post(GROUP_PATH).header("User", accountDto.getIdField())
                .param("type", groupType.toString())
                .param("name", groupName)
                .param("description", groupDescription)).andDo(print())
                .andExpect(jsonPath("$.name", is(groupName)))
                .andExpect(jsonPath("$.description", is(groupDescription)))
                .andExpect(jsonPath("$.type", is(groupType.toString())))
                .andExpect(jsonPath("$.members[0].accountUsername", is(accountDto.getUsername())))
                .andExpect(jsonPath("$.members[0].accountId", is(accountDto.getIdField())))
                .andExpect(jsonPath("$.members[0].groupName", is(groupName)))
                .andExpect(jsonPath("$.members[0].role", is(GroupRole.LEADER.toString())));
        numOfGroupsCreated++;
    }

    @Test
    public void addMemberToGroup() throws Exception {
        AccountDto leader = createDefaultAccount();
        AccountDto member = createDefaultAccount();
        GroupDto groupDto = createDefaultGroup(leader);
        this.mockMvc.perform(post(ACCOUNTS_PATH + "/" + member.getUsername() + "/group/" + groupDto.getIdField())
                .header("User", leader.getIdField())
                .param("role", GroupRole.MEMBER.toString()))
                .andDo(print())
                .andExpect(jsonPath("$.accountUsername", is(member.getUsername())))
                .andExpect(jsonPath("$.accountId", is(member.getIdField())))
                .andExpect(jsonPath("$.addedByUsername", is(leader.getUsername())))
                .andExpect(jsonPath("$.addedById", is(leader.getIdField())))
                .andExpect(jsonPath("$.groupId", is(groupDto.getIdField())))
                .andExpect(jsonPath("$.groupName", is(groupDto.getName())))
                .andExpect(jsonPath("$.role", is(GroupRole.MEMBER.toString())));
        this.mockMvc.perform(get(GROUP_PATH + "/" + groupDto.getIdField())
                .header("User", leader.getIdField())).andDo(print())
                .andExpect(jsonPath("$.name", is(groupDto.getName())))
                .andExpect(jsonPath("$.description", is(groupDto.getDescription())))
                .andExpect(jsonPath("$.type", is(groupDto.getType().toString())))
                .andExpect(jsonPath("$.members[0].accountUsername", is(leader.getUsername())))
                .andExpect(jsonPath("$.members[0].accountId", is(leader.getIdField())))
                .andExpect(jsonPath("$.members[0].role", is(GroupRole.LEADER.toString())))
                .andExpect(jsonPath("$.members[1].accountUsername", is(member.getUsername())))
                .andExpect(jsonPath("$.members[1].accountId", is(member.getIdField())))
                .andExpect(jsonPath("$.members[1].role", is(GroupRole.MEMBER.toString())));
    }

    @Test
    public void addProjectToGroup() throws Exception {
        Integer projectId = 1;
        String projectName = "SomeProject";
        AccountDto leader = createDefaultAccount();
        GroupDto groupDto = createDefaultGroup(leader);
        this.mockMvc.perform(post(GROUP_PATH + "/" + groupDto.getIdField() + "/projects")
                .header("User", leader.getIdField())
                .param("projectName", projectName)
                .param("projectId", String.valueOf(projectId)))
                .andDo(print())
                .andExpect(jsonPath("$.projectId", is(projectId)))
                .andExpect(jsonPath("$.projectName", is(projectName)))
                .andExpect(jsonPath("$.groupName", is(groupDto.getName())))
                .andExpect(jsonPath("$.groupId", is(groupDto.getIdField())))
                .andExpect(jsonPath("$.addedByUsername", is(leader.getUsername())))
                .andExpect(jsonPath("$.addedById", is(leader.getIdField())));
        this.mockMvc.perform(get(GROUP_PATH + "/" + groupDto.getIdField())
                .header("User", leader.getIdField())).andDo(print())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(groupDto.getName())))
                .andExpect(jsonPath("$.description", is(groupDto.getDescription())))
                .andExpect(jsonPath("$.type", is(groupDto.getType().toString())))
                .andExpect(jsonPath("$.projects[0].idField", is(projectId)))
                .andExpect(jsonPath("$.projects[0].projectName", is(projectName)))
                .andExpect(jsonPath("$.projects[0].groupName", is(groupDto.getName())))
                .andExpect(jsonPath("$.projects[0].groupId", is(groupDto.getIdField())))
                .andExpect(jsonPath("$.projects[0].addedByUsername", is(leader.getUsername())))
                .andExpect(jsonPath("$.projects[0].addedById", is(leader.getIdField())));
    }

    @Test
    public void searchForGroupByName() throws Exception {
        AccountDto leader = createDefaultAccount();
        GroupDto groupDto = createGroupWithName(leader, "TestingGroupAlpha");
        this.mockMvc.perform(get(GROUP_PATH + "/search").header("User", leader.getIdField()).param("name", groupDto.getName()))
                .andDo(print())
                .andExpect(jsonPath("$._embedded.content[0].name", is(groupDto.getName())))
                .andExpect(jsonPath("$._embedded.content[0].description", is(groupDto.getDescription())))
                .andExpect(jsonPath("$._embedded.content[0].type", is(groupDto.getType().toString())))
                .andExpect(jsonPath("$._embedded.content[0].members[0].accountUsername", is(leader.getUsername())))
                .andExpect(jsonPath("$._embedded.content[0].members[0].accountId", is(leader.getIdField())))
                .andExpect(jsonPath("$._embedded.content[0].members[0].groupName", is(groupDto.getName())))
                .andExpect(jsonPath("$._embedded.content[0].members[0].role", is(GroupRole.LEADER.toString())))
                .andExpect(jsonPath("$.page.totalElements", is(1)));
    }
}
