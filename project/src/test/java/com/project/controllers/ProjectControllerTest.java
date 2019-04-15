package com.project.controllers;

import com.common.models.dtos.ProjectDto;
import com.project.services.ProjectTest;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class ProjectControllerTest extends ProjectTest {

    @Test
    public void findAll() throws Exception {
        createDefaultProject();
        createDefaultProject();
        this.mockMvc.perform(get(PROJECTS_PATH))
                .andDo(print())
                .andExpect(jsonPath("$._embedded.projectDtoList", iterableWithSize(numofProjectsCreated)))
                .andExpect(jsonPath("$._links.self.href", is(this.hostname + "projects?page=0&size=20")))
                .andExpect(jsonPath("$.page.size", is(20)))
                .andExpect(jsonPath("$.page.totalElements", is(numofProjectsCreated)))
                .andExpect(jsonPath("$.page.totalPages", is(1)))
                .andExpect(jsonPath("$.page.number", is(0)));
    }

    @Test
    public void findById() throws Exception {
        ProjectDto projectDto = createDefaultProject();
        this.mockMvc.perform(get(PROJECTS_PATH + projectDto.getProjectId()))
                .andDo(print())
                .andExpect(jsonPath("$.projectId", is(projectDto.getProjectId())))
                .andExpect(jsonPath("$.title", is(projectDto.getTitle())))
                .andExpect(jsonPath("$.synopsis", is(projectDto.getSynopsis())))
                .andExpect(jsonPath("$.type", is(projectDto.getType())))
                .andExpect(jsonPath("$.visibilityType", is(projectDto.getVisibilityType())))
                .andExpect(jsonPath("$.sourcingType", is(projectDto.getSourcingType())))
                .andExpect(jsonPath("$.originalAuthor", is(projectDto.getOriginalAuthor())))
                .andExpect(jsonPath("$.creationDate", is(projectDto.getCreationDate())))
                .andExpect(jsonPath("$.modifiedDate", is(projectDto.getModifiedDate())))
                .andExpect(jsonPath("$.copy.idField", is(projectDto.getCopy().getIdField())))
                .andExpect(jsonPath("$.copy.projectId", is(projectDto.getCopy().getProjectId())))
                .andExpect(jsonPath("$.copy.value", is(projectDto.getCopy().getValue())))
                .andExpect(jsonPath("$.copy._links.self.href", is(this.hostname + "copy/" + projectDto.getCopy().getIdField())))
                .andExpect(jsonPath("$.parts", is(projectDto.getParts())))
                .andExpect(jsonPath("$.roles[0].idField", is(projectDto.getRoles().get(0).getIdField())))
                .andExpect(jsonPath("$.roles[0].authorId", is(projectDto.getRoles().get(0).getAuthorId())))
                .andExpect(jsonPath("$.roles[0].authorName", is(projectDto.getRoles().get(0).getAuthorName())))
                .andExpect(jsonPath("$.roles[0].projectId", is(projectDto.getRoles().get(0).getProjectId())))
                .andExpect(jsonPath("$.roles[0].projectTitle", is(projectDto.getRoles().get(0).getProjectTitle())))
                .andExpect(jsonPath("$.roles[0].role", is(projectDto.getRoles().get(0).getRole().toString())))
                .andExpect(jsonPath("$.roles[0]._links.self.href", is(this.hostname + "roles/" + projectDto.getRoles().get(0).getIdField())))
                .andExpect(jsonPath("$.tags", is(projectDto.getTags())))
                .andExpect(jsonPath("$.version", is(0)))
                .andExpect(jsonPath(selfLink, is(this.hostname + "projects/" + projectDto.getProjectId())));
    }

    @Test
    public void createProject() throws Exception {
        String testTitle = "TestTitle!";
        String testSynopsis = "TestSynopsis!";
        super.createProjectWithTitleAndSynopsis(testTitle, testSynopsis)
                .andDo(print())
                .andExpect(jsonPath("$.title", is(testTitle)))
                .andExpect(jsonPath("$.synopsis", is(testSynopsis)))
                .andExpect(jsonPath("$.type", is(defaultProjectType)))
                .andExpect(jsonPath("$.visibilityType", is(defaultVisibilityType)))
                .andExpect(jsonPath("$.sourcingType", is(defaultSourcingType)))
                .andExpect(jsonPath("$.originalAuthor", is(defaultAuthorName)))
                .andExpect(jsonPath("$.copy.value", is("")))
                .andExpect(jsonPath("$.roles[0].authorName", is(defaultAuthorName)))
                .andExpect(jsonPath("$.roles[0].projectTitle", is(testTitle)))
                .andExpect(jsonPath("$.roles[0].role", is("CREATOR")));

    }

    @Test
    public void updateAProject() throws Exception {
        String newTitle = "Updated";
        String newSynopsis = "UpdatedSynopsis";
        ProjectDto updateableProject = createDefaultProject();
        this.mockMvc.perform(patch(PROJECTS_PATH + updateableProject.getProjectId())
                .param("title", newTitle)
                .param("synopsis", newSynopsis)
                .header("User", defaultUserId))
                .andDo(print())
                .andExpect(jsonPath("$.projectId", is(updateableProject.getProjectId())))
                .andExpect(jsonPath("$.title", is(newTitle)))
                .andExpect(jsonPath("$.synopsis", is(newSynopsis)))
                .andExpect(jsonPath("$.type", is(updateableProject.getType())))
                .andExpect(jsonPath("$.visibilityType", is(updateableProject.getVisibilityType())))
                .andExpect(jsonPath("$.sourcingType", is(updateableProject.getSourcingType())))
                .andExpect(jsonPath("$.originalAuthor", is(updateableProject.getOriginalAuthor())))
                .andExpect(jsonPath("$.creationDate", is(updateableProject.getCreationDate())))
                .andExpect(jsonPath("$.modifiedDate", not(updateableProject.getModifiedDate())))
                .andExpect(jsonPath("$.copy.idField", is(updateableProject.getCopy().getIdField())))
                .andExpect(jsonPath("$.copy.projectId", is(updateableProject.getCopy().getProjectId())))
                .andExpect(jsonPath("$.copy.value", is(updateableProject.getCopy().getValue())))
                .andExpect(jsonPath("$.copy._links.self.href", is(this.hostname + "copy/" + updateableProject.getCopy().getIdField())))
                .andExpect(jsonPath("$.parts", is(updateableProject.getParts())))
                .andExpect(jsonPath("$.roles[0].idField", is(updateableProject.getRoles().get(0).getIdField())))
                .andExpect(jsonPath("$.roles[0].authorId", is(updateableProject.getRoles().get(0).getAuthorId())))
                .andExpect(jsonPath("$.roles[0].authorName", is(updateableProject.getRoles().get(0).getAuthorName())))
                .andExpect(jsonPath("$.roles[0].projectId", is(updateableProject.getRoles().get(0).getProjectId())))
                .andExpect(jsonPath("$.roles[0].projectTitle", is(newTitle)))
                .andExpect(jsonPath("$.roles[0].role", is(updateableProject.getRoles().get(0).getRole().toString())))
                .andExpect(jsonPath("$.roles[0]._links.self.href", is(this.hostname + "roles/" + updateableProject.getRoles().get(0).getIdField())))
                .andExpect(jsonPath("$.tags", is(updateableProject.getTags())))
                .andExpect(jsonPath("$.version", is(1)))
                .andExpect(jsonPath(selfLink, is(this.hostname + "projects/" + updateableProject.getProjectId())));
    }
}