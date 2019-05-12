package com.project.controllers;

import com.common.models.dtos.AuthorProjectRoleDto;
import com.common.models.dtos.AuthorProjectRoleType;
import com.common.models.dtos.ProjectDto;
import com.project.dao.entites.Author;
import com.project.services.ProjectTest;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class AuthorProjectRoleControllerTest extends ProjectTest {

    @Test
    public void findById() throws Exception {
        ProjectDto project = createDefaultProject();
        AuthorProjectRoleDto authorProjectRole = project.getRoles().get(0);
        this.mockMvc.perform(get(ROLES_PATH + authorProjectRole.getIdField()).header("User", defaultUserId))
                .andDo(print())
                .andExpect(jsonPath("$.idField", is(authorProjectRole.getIdField())))
                .andExpect(jsonPath("$.authorId", is(authorProjectRole.getAuthorId())))
                .andExpect(jsonPath("$.authorName", is(authorProjectRole.getAuthorName())))
                .andExpect(jsonPath("$.projectId", is(authorProjectRole.getProjectId())))
                .andExpect(jsonPath("$.projectTitle", is(authorProjectRole.getProjectTitle())))
                .andExpect(jsonPath("$.role", is(authorProjectRole.getRole().toString())))
                .andExpect(jsonPath(selfLink, is(hostname + "roles/" + authorProjectRole.getIdField())));
    }

    @Test
    public void findAll() throws Exception {
        createDefaultProject();
        this.mockMvc.perform(get(ROLES_PATH).header("User", defaultUserId))
                .andDo(print())
                .andExpect(jsonPath("$._embedded.content", is(iterableWithSize(numOfAuthorProjectRolesCreated))))
                .andExpect(jsonPath(selfLink, is(hostname + "roles?page=0&size=20")))
                .andExpect(jsonPath("$.page.size", is(20)))
                .andExpect(jsonPath("$.page.totalElements", is(numOfAuthorProjectRolesCreated)))
                .andExpect(jsonPath("$.page.totalPages", is(1)))
                .andExpect(jsonPath("$.page.number", is(0)));
    }

    @Test
    public void postRole() throws Exception {
        Integer newUserId = 3;
        String newAuthorName = "SomeGuyd";
        Author newAuthor = createNewAuthor(newUserId, newAuthorName);
        ProjectDto newProject = createDefaultProject();
        numOfAuthorProjectRolesCreated++;
        this.mockMvc.perform(post(PROJECTS_PATH + newProject.getProjectId() + "/roles").header("User", defaultUserId)
                .param("userId", String.valueOf(newUserId))
                .param("authorProjectRoleType", AuthorProjectRoleType.CONTRIBUTOR.toString()))
                .andDo(print())
                .andExpect(jsonPath("$.authorId", is(newAuthor.getAuthorId())))
                .andExpect(jsonPath("$.authorName", is(newAuthor.getUsername())))
                .andExpect(jsonPath("$.projectId", is(newProject.getProjectId())))
                .andExpect(jsonPath("$.projectTitle", is(newProject.getTitle())))
                .andExpect(jsonPath("$.role", is(AuthorProjectRoleType.CONTRIBUTOR.toString())));
    }

    @Test
    public void patchRole() throws Exception {
        Integer newUserId = 7;
        String newAuthorName = "Johans";
        ProjectDto createdProject = createDefaultProject();
        Author author = createNewAuthor(newUserId, newAuthorName);
        AuthorProjectRoleDto patchingAuthorRole = createAuthorRole(String.valueOf(newUserId),
                String.valueOf(createdProject.getProjectId()),
                AuthorProjectRoleType.CONTRIBUTOR);
        this.mockMvc.perform(get(ROLES_PATH + patchingAuthorRole.getIdField())
                .header("User", defaultUserId))
                .andDo(print())
                .andExpect(jsonPath("$.idField", is(patchingAuthorRole.getIdField())))
                .andExpect(jsonPath("$.role", is(patchingAuthorRole.getRole().toString())));
        this.mockMvc.perform(patch(ROLES_PATH + patchingAuthorRole.getIdField()).header("User", defaultUserId)
                .param("newRole", AuthorProjectRoleType.BARRED.toString()))
                .andDo(print())
                .andExpect(jsonPath("$.idField", is(patchingAuthorRole.getIdField())))
                .andExpect(jsonPath("$.role", is(AuthorProjectRoleType.BARRED.toString())));
        this.mockMvc.perform(get(ROLES_PATH + patchingAuthorRole.getIdField())
                .header("User", defaultUserId))
                .andDo(print())
                .andExpect(jsonPath("$.idField", is(patchingAuthorRole.getIdField())))
                .andExpect(jsonPath("$.role", is(AuthorProjectRoleType.BARRED.toString())));
    }
}