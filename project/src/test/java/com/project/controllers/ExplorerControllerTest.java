package com.project.controllers;

import com.common.models.dtos.ProjectDto;
import com.project.services.ProjectTest;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ExplorerControllerTest extends ProjectTest {

    @Test
    public void searchPartialTitle() throws Exception {
        ProjectDto projectDto = createProjectWithTitleSynopsisAndUserId("SomeHorrid", "Synopsis", defaultUserId);
        this.mockMvc.perform(get(EXPLORE_PATH).header("User", defaultUserId)
                .param("title", projectDto.getTitle()))
                .andDo(print())
                .andExpect(jsonPath("$.page.totalElements", is(1)));
        this.mockMvc.perform(get(EXPLORE_PATH).header("User", defaultUserId)
                .param("title", projectDto.getTitle().substring(0, projectDto.getTitle().length() - 1)))
                .andDo(print())
                .andExpect(jsonPath("$.page.totalElements", is(1)));
        this.mockMvc.perform(get(EXPLORE_PATH).header("User", defaultUserId)
                .param("title", projectDto.getTitle() + "1"))
                .andExpect(jsonPath("$.page.totalElements", is(0)));
    }

    @Test
    public void searchTags() throws Exception {
        ProjectDto projectDto = createDefaultProject();
        this.mockMvc.perform(post(PROJECTS_PATH + projectDto.getProjectId() + "/tags")
                .param("value", "NSFW")
                .header("User", defaultUserId));
        numOfTagsCreated++;
        this.mockMvc.perform(get(EXPLORE_PATH).header("User", defaultUserId)
                .accept(MediaType.APPLICATION_JSON)
                .param("tags", "NSFW"))
                .andDo(print())
                .andExpect(jsonPath("$._embedded.content[0].projectId", is(projectDto.getProjectId())))
                .andExpect(jsonPath("$._embedded.content[0].tags[0].value", is("NSFW")))
                .andExpect(jsonPath("$.page.totalElements", is(1)));
        this.mockMvc.perform(get(EXPLORE_PATH).header("User", defaultUserId)
                .param("tags", defaultTagValue + 1))
                .andDo(print())
                .andExpect(jsonPath("$.page.totalElements", is(0)));
        this.mockMvc.perform(post(PROJECTS_PATH + projectDto.getProjectId() + "/tags")
                .param("value", "NewTag")
                .header("User", defaultUserId))
                .andDo(print())
                .andExpect(status().isOk());
        numOfTagsCreated++;
        this.mockMvc.perform(get(EXPLORE_PATH).header("User", defaultUserId)
                .param("tags", defaultTagValue  + "," + "NewTag"))
                .andDo(print())
                .andExpect(jsonPath("$._embedded.content[0].projectTitle", is(projectDto.getTitle())))
                .andExpect(jsonPath("$._embedded.content[0].tags[0].value", is("NSFW")))
                .andExpect(jsonPath("$._embedded.content[0].tags[1].value", is("NewTag")))
                .andExpect(jsonPath("$.page.totalElements", is(1)));
    }

    @Test
    public void searchByAuthorName() throws Exception {
        Integer explorerUserId = 5;
        String explorerName = "DoraThe";
        createNewAuthor(explorerUserId, explorerName);
        createProjectWithTitleSynopsisAndUserId("DoraGoesTo", "Spain!", String.valueOf(explorerUserId));
        this.mockMvc.perform(get(EXPLORE_PATH).header("User", defaultUserId)
                .param("author", explorerName))
                .andDo(print())
                .andExpect(jsonPath("$.page.totalElements", is(1)));
    }

}
