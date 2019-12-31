package com.project.controllers;

import com.common.models.enums.AuthorProjectRoleType;
import com.common.models.dtos.ProjectDto;
import com.project.dao.entites.Author;
import com.project.services.ProjectTest;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class AuthorControllerTest extends ProjectTest {

    @Test
    public void getAuthors() throws Exception {
        this.mockMvc.perform(get(AUTHOR_PATH).header("User", defaultUserId))
                .andDo(print())
                .andExpect(jsonPath("$._embedded.content", iterableWithSize(numOfAuthorsCreated)))
                .andExpect(jsonPath(selfLink, is(hostname + "authors?page=0&size=20")))
                .andExpect(jsonPath("$.page.size", is(20)))
                .andExpect(jsonPath("$.page.totalElements", is(numOfAuthorsCreated)));
    }

    @Test
    public void getThisAuthor() throws Exception {
        Integer userId = 12;
        String authorName = "SomeGuy";
        Author newAuthor = createNewAuthor(userId, authorName);
        this.mockMvc.perform(get(AUTHOR_PATH + "/thisAuthor").header("User", userId))
                .andDo(print())
                .andExpect(jsonPath("$.idField", is(newAuthor.getAuthorId())))
                .andExpect(jsonPath("$.userId", is(userId)))
                .andExpect(jsonPath("$.username", is(authorName)))
                .andExpect(jsonPath("$.createdProjects", emptyIterable()))
                .andExpect(jsonPath("$.createdParts", emptyIterable()))
                .andExpect(jsonPath(selfLink, is (hostname + "authors/" + newAuthor.getAuthorId())));
        String projectName = "SomeNewProject";
        String synopsisName = "SomeNewSynopsis";
        ProjectDto createdProject = createProjectWithTitleSynopsisAndUserId(projectName, synopsisName, String.valueOf(userId));
        this.mockMvc.perform(get(AUTHOR_PATH + "/thisAuthor").header("User", userId))
                .andDo(print())
                .andExpect(jsonPath("$.idField", is(newAuthor.getAuthorId())))
                .andExpect(jsonPath("$.createdProjects[0].title", is(projectName)))
                .andExpect(jsonPath("$.createdProjects[0].synopsis", is(synopsisName)))
                .andExpect(jsonPath("$.createdProjects[0].projectId", is(createdProject.getProjectId())))
                .andExpect(jsonPath("$.createdProjects[0].originalAuthor", is(authorName)))
                .andExpect(jsonPath("$.createdProjects[0].roles[0].authorId", is(newAuthor.getAuthorId())))
                .andExpect(jsonPath("$.createdProjects[0].roles[0].authorName", is(newAuthor.getUsername())))
                .andExpect(jsonPath("$.createdProjects[0].roles[0].role", is(AuthorProjectRoleType.CREATOR.toString())))
                .andExpect(jsonPath("$.createdParts", emptyIterable()));
    }

    @Test
    public void getAuthorById() throws Exception {
        Integer newUserId = 41;
        String authorName = "SomeGuy";
        Author author = createNewAuthor(newUserId, authorName);
        this.mockMvc.perform(get(AUTHOR_PATH + author.getUserId()).header("User", defaultUserId))
                .andDo(print())
                .andExpect(jsonPath("$.idField", is(author.getAuthorId())))
                .andExpect(jsonPath("$.userId", is(author.getUserId())))
                .andExpect(jsonPath("$.username", is(author.getUsername())))
                .andExpect(jsonPath("$.createdProjects", emptyIterable()))
                .andExpect(jsonPath("$.createdParts", emptyIterable()));
    }
}