package com.project.controllers;

import com.common.models.enums.PartStatus;
import com.common.models.dtos.ProjectDto;
import com.common.models.dtos.ProjectPartDto;
import com.project.services.ProjectTest;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PartControllerTest extends ProjectTest {

    @Test
    public void requestNextPart() throws Exception {
        ProjectDto projectDto = createDefaultProject();
        numOfPartsRequested++;
        this.mockMvc.perform(post(PARTS_PATH + "project/" + projectDto.getProjectId() + " /parts").header("User", defaultUserId))
                .andDo(print())
                .andExpect(jsonPath("$.projectId", is(projectDto.getProjectId())))
                .andExpect(jsonPath("$.status", is(PartStatus.IN_PROGRESS.toString())))
                .andExpect(jsonPath("$.activeValue", is("")))
                .andExpect(jsonPath("$.sequence", is(1)))
                .andExpect(jsonPath("$.authorName", is(defaultAuthorName)));

    }

    @Test
    public void addValueToPart() throws Exception {
        String someValue = "SomeValue";
        String patchStatus = PartStatus.ACTIVE.toString();
        ProjectDto projectDto = createDefaultProject();
        ProjectPartDto partDto = createDefaultPartOnProjectId(String.valueOf(projectDto.getProjectId()));
        this.mockMvc.perform(patch(PARTS_PATH + partDto.getIdField())
                .header("User", defaultUserId)
                .param("value", someValue)
                .param("reviewStatus", patchStatus))
                .andDo(print())
                .andExpect(jsonPath("$.idField", is(partDto.getIdField())))
                .andExpect(jsonPath("$.projectId", is(partDto.getProjectId())))
                .andExpect(jsonPath("$.status", is(PartStatus.IN_PROGRESS.toString())))
                .andExpect(jsonPath("$.activeValue", is(someValue)))
                .andExpect(jsonPath("$.sequence", is(1)))
                .andExpect(jsonPath("$.authorName", is(defaultAuthorName)))
                .andExpect(jsonPath(selfLink, is(hostname + "parts/" + partDto.getIdField())));
        this.mockMvc.perform(get(PROJECTS_PATH + projectDto.getProjectId()))
                .andDo(print())
                .andExpect(jsonPath("$.parts[0].idField", is(partDto.getIdField())))
                .andExpect(jsonPath("$.copy.value", isEmptyString()));
        patchStatus = PartStatus.LOCKED.toString();
        this.mockMvc.perform(patch(PARTS_PATH + partDto.getIdField())
                .header("User", defaultUserId)
                .param("value", someValue)
                .param("reviewStatus", patchStatus))
                .andExpect(jsonPath("$.status", is(patchStatus)));
        this.mockMvc.perform(get(PROJECTS_PATH + projectDto.getProjectId()))
                .andDo(print())
                .andExpect(jsonPath("$.parts[0].idField", is(partDto.getIdField())))
                .andExpect(jsonPath("$.copy.value", is(" " + someValue)));
    }

    @Test
    public void deletePart() throws Exception {
        ProjectDto projectDto = createDefaultProject();
        ProjectPartDto partDto = createDefaultPartOnProjectId((String.valueOf(projectDto.getProjectId())));
        this.mockMvc.perform(get(PARTS_PATH + "/" + partDto.getIdField())
                .header("User", defaultUserId))
                .andDo(print())
                .andExpect(jsonPath("$.projectId", is(projectDto.getProjectId())))
                .andExpect(jsonPath("$.status", is(PartStatus.IN_PROGRESS.toString())))
                .andExpect(jsonPath("$.authorName", is(defaultAuthorName)))
                .andExpect(jsonPath("$.projectTitle", is(projectDto.getTitle())));
        this.mockMvc.perform(patch((PARTS_PATH + "/" + partDto.getIdField().toString() + "/delete"))
                .header("User", defaultUserId))
                .andExpect(status().isOk());
        this.mockMvc.perform(get(PARTS_PATH + "/" + partDto.getIdField().toString())
                .header("User", defaultUserId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}