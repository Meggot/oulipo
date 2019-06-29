package com.project.controllers;

import com.common.models.dtos.*;
import com.common.models.messages.MessageType;
import com.common.models.utils.ReadWriteUtils;
import com.project.services.ProjectTest;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


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

    @Test
    public void addCopyEditToCopyCreator() throws Exception {
        String initialValue = "There's a little boy outside, and he cnt find his way h0me";
        String patchtoApply = "@@ -7,15 +7,8 @@\n" +
                " s a \n" +
                "-little \n" +
                " boy \n" +
                "@@ -24,17 +24,19 @@\n" +
                " and he c\n" +
                "-n\n" +
                "+an'\n" +
                " t find h\n" +
                "@@ -47,7 +47,28 @@\n" +
                " ay h\n" +
                "-0\n" +
                "+o\n" +
                " me\n" +
                "+. He's probably lost!";
        String patchedValue = "There's a boy outside, and he can't find his way home. He's probably lost!";
        ProjectDto projectDto = createDefaultProject();
        ProjectPartDto partDto = createDefaultPartOnProjectId(String.valueOf(projectDto.getProjectId()));
        this.mockMvc.perform(patch(PARTS_PATH + partDto.getIdField())
                .header("User", defaultUserId)
                .param("value", initialValue)
                .param("reviewStatus", PartStatus.LOCKED.toString()));
        this.mockMvc.perform(get(COPY_PATH + projectDto.getCopy().getIdField())
                .header("User", defaultUserId))
                .andDo(print())
                .andExpect(jsonPath("$.idField", is(projectDto.getCopy().getIdField())))
                .andExpect(jsonPath("$.projectId", is(projectDto.getProjectId())))
                .andExpect(jsonPath("$.value", is(" " + initialValue)))
                .andExpect(jsonPath(selfLink, is(hostname + "copy/" + projectDto.getCopy().getIdField())));
        String copyEditValue =
                this.mockMvc.perform(post(COPY_PATH + projectDto.getCopy().getIdField() + "/edits")
                        .header("User", defaultUserId)
                        .param("delta", patchtoApply))
                        .andDo(print())
                        .andExpect(jsonPath("$.delta", is(patchtoApply)))
                        .andExpect(jsonPath("$.authorName", is(defaultAuthorName)))
                        .andExpect(jsonPath("$.projectTitle", is(projectDto.getTitle())))
                        .andExpect(jsonPath("$.projectId", is(projectDto.getProjectId())))
                        .andExpect(jsonPath("$.copyId", is(projectDto.getCopy().getIdField())))
                        .andExpect(jsonPath("$.status", is(CopyEditStatus.APPLIED.toString())))
                        .andReturn().getResponse().getContentAsString();
        numOfEditsCreated++;
        assertThat(getNumberOfEventsInProjectStreamer(MessageType.COPY_EDIT_CREATION)).isEqualTo(numOfEditsCreated);
        CopyEditDto copyEditDto = ReadWriteUtils.asObjectFromString(CopyEditDto.class, copyEditValue);
        this.mockMvc.perform(get(COPY_PATH + projectDto.getCopy().getIdField())
                .header("User", defaultUserId))
                .andDo(print())
                .andExpect(jsonPath("$.idField", is(projectDto.getCopy().getIdField())))
                .andExpect(jsonPath("$.projectId", is(projectDto.getProjectId())))
                .andExpect(jsonPath("$.value", is(" " + patchedValue)))
                .andExpect(jsonPath("$.edits[0].idField", is(copyEditDto.getIdField())))
                .andExpect(jsonPath("$.edits[0].delta", is(patchtoApply)))
                .andExpect(jsonPath("$.edits[0].authorName", is(defaultAuthorName)))
                .andExpect(jsonPath("$.edits[0].projectTitle", is(defaultTitle)))
                .andExpect(jsonPath("$.edits[0].status", is(CopyEditStatus.APPLIED.toString())));
        // NEED NOT APPROVE AS TEST USER IS OWNER
//        this.mockMvc.perform(patch(EDIT_PATH + copyEditDto.getIdField() + "/action")
//                .header("User", defaultUserId)
//                .param("action", EditActionType.APPROVE.toString()))
//                .andDo(print())
//                .andExpect(jsonPath("$.status", is(CopyEditStatus.APPLIED.toString())));
        assertThat(getNumberOfEventsInProjectStreamer(MessageType.COPY_EDIT_UPDATE)).isEqualTo(1);
        this.mockMvc.perform(get(COPY_PATH + projectDto.getCopy().getIdField())
                .header("User", defaultUserId))
                .andDo(print())
                .andExpect(jsonPath("$.idField", is(projectDto.getCopy().getIdField())))
                .andExpect(jsonPath("$.projectId", is(projectDto.getProjectId())))
                .andExpect(jsonPath("$.value", is(" " + patchedValue)))
                .andExpect(jsonPath("$.edits[0].idField", is(copyEditDto.getIdField())))
                .andExpect(jsonPath("$.edits[0].delta", is(patchtoApply)))
                .andExpect(jsonPath("$.edits[0].authorName", is(defaultAuthorName)))
                .andExpect(jsonPath("$.edits[0].projectTitle", is(defaultTitle)))
                .andExpect(jsonPath("$.edits[0].status", is(CopyEditStatus.APPLIED.toString())));
    }
}