package com.project.controllers;

import com.common.models.dtos.ProjectDto;
import com.project.services.ProjectTest;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


public class CopyControllerTest extends ProjectTest {

    @Test
    public void getCopyById() throws Exception {
        ProjectDto projectDto = createDefaultProject();
        this.mockMvc.perform(get(COPY_PATH + projectDto.getCopy().getIdField())
                .header("User", defaultUserId))
                .andDo(print())
                .andExpect(jsonPath("$.idField", is(projectDto.getCopy().getIdField())))
                .andExpect(jsonPath("$.projectId", is(projectDto.getProjectId())))
                .andExpect(jsonPath("$.value", isEmptyString()))
                .andExpect(jsonPath(selfLink, is(hostname + "copy/" + projectDto.getCopy().getIdField())));
    }
}