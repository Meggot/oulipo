package com.project.controllers;

import com.common.models.dtos.ProjectDto;
import com.common.models.dtos.ProjectTagDto;
import com.common.models.dtos.TagType;
import com.project.services.ProjectTest;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProjectTagControllerTest extends ProjectTest {

    @Test
    public void findTags() throws Exception {
        ProjectDto projectDto = createDefaultProject();
        createDefaultTagOnProjectId(String.valueOf(projectDto.getProjectId()));
        createDefaultTagOnProjectId(String.valueOf(projectDto.getProjectId()));
        this.mockMvc.perform(get(TAGS_PATH).header("User", defaultUserId))
                .andDo(print())
                .andExpect(jsonPath("$._embedded.projectTagDtoList", iterableWithSize(numOfTagsCreated)))
                .andExpect(jsonPath("$._links.self.href", is(hostname + "tags?page=0&size=20")))
                .andExpect(jsonPath("$.page.size", is(20)))
                .andExpect(jsonPath("$.page.totalElements", is(numOfTagsCreated)))
                .andExpect(jsonPath("$.page.totalPages", is(1)))
                .andExpect(jsonPath("$.page.number", is(0)));

    }

    @Test
    public void findTagById() throws Exception{
        ProjectDto defaultProject = createDefaultProject();
        ProjectTagDto projectTagDto = createDefaultTagOnProjectId(String.valueOf(defaultProject.getProjectId()));
        this.mockMvc.perform(get(TAGS_PATH + projectTagDto.getIdField()).header("User", defaultUserId))
                .andDo(print())
                .andExpect(jsonPath("$.idField", is(projectTagDto.getIdField())))
                .andExpect(jsonPath("$.projectTitle", is(projectTagDto.getProjectTitle())))
                .andExpect(jsonPath("$.type", is(projectTagDto.getType().toString())))
                .andExpect(jsonPath("$.userIdAdded", is(projectTagDto.getUserIdAdded())))
                .andExpect(jsonPath("$.value", is(projectTagDto.getValue())))
                .andExpect(jsonPath(selfLink, is(hostname + "tags/" + projectTagDto.getIdField())));
        this.mockMvc.perform(get(PROJECTS_PATH + defaultProject.getProjectId()))
                .andDo(print())
                .andExpect(jsonPath("$.tags[0].idField", is(projectTagDto.getIdField())))
                .andExpect(jsonPath("$.tags[0].projectTitle", is(projectTagDto.getProjectTitle())))
                .andExpect(jsonPath("$.tags[0].type", is(projectTagDto.getType().toString())))
                .andExpect(jsonPath("$.tags[0].userIdAdded", is(projectTagDto.getUserIdAdded())))
                .andExpect(jsonPath("$.tags[0].value", is(projectTagDto.getValue())))
                .andExpect(jsonPath("$.tags[0]._links.self.href", is(hostname + "tags/" + projectTagDto.getIdField())));
    }

    @Test
    public void postTag() throws Exception {
        String tagValue = "Tagged";
        ProjectDto project = createDefaultProject();
        numOfTagsCreated++;
        this.mockMvc.perform(post(TAGS_PATH)
                .param("projectId", String.valueOf(project.getProjectId()))
                .param("value", tagValue)
                .header("User", defaultUserId))
                .andDo(print())
                .andExpect(jsonPath("$.projectTitle", is(project.getTitle())))
                .andExpect(jsonPath("$.type", is(TagType.USER_ADDED.toString())))
                .andExpect(jsonPath("$.userIdAdded", is(Integer.valueOf(defaultUserId))))
                .andExpect(jsonPath("$.value", is(tagValue)));
    }

    @Test
    public void deleteTag() throws Exception {
        ProjectDto project = createDefaultProject();
        ProjectTagDto tagDto = createDefaultTagOnProjectId(String.valueOf(project.getProjectId()));
        this.mockMvc.perform(get(TAGS_PATH + tagDto.getIdField()).header("User", defaultUserId))
                .andExpect(jsonPath("$.idField", is(tagDto.getIdField())));
        this.mockMvc.perform(get(PROJECTS_PATH + project.getProjectId()).header("User", defaultUserId))
                .andExpect(jsonPath("$.tags[0].idField", is(tagDto.getIdField())));
        this.mockMvc.perform(delete(TAGS_PATH + tagDto.getIdField()).header("User", defaultUserId))
                .andExpect(status().isAccepted());
        this.mockMvc.perform(get(TAGS_PATH + tagDto.getIdField()).header("User", defaultUserId))
                .andExpect(status().isNotFound());
        this.mockMvc.perform(get(PROJECTS_PATH + project.getProjectId()).header("User", defaultUserId))
                .andExpect(jsonPath("$.tags", emptyIterable()));
    }
}