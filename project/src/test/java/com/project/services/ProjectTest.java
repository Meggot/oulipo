package com.project.services;

import com.common.models.dtos.*;
import com.common.models.messages.AccountCreationMessage;
import com.common.models.utils.ReadWriteUtils;
import com.project.dao.entites.Author;
import com.project.dao.repository.AuthorRepository;
import com.project.streaming.AuthorLifecycleListener;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(properties = "spring.profiles.active = Test")
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ProjectTest {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public AuthorLifecycleListener authorLifecycleListener;

    @Autowired
    public AuthorRepository authorRepository;

    public String EXPLORE_PATH = "/explore/";

    public String PROJECTS_PATH = "/projects/";

    public String PARTS_PATH = "/parts/";

    public String TAGS_PATH = "/tags/";

    public String AUTHOR_PATH = "/authors/";

    public String COPY_PATH = "/copy/";

    public String EDIT_PATH = "/edits/";

    public String ROLES_PATH = "/roles/";
    public String hostname = "http://localhost/";

    public static String selfLink = "$._links.self.href";

    public String defaultProjectType = ProjectType.STORY.toString();

    public String defaultVisibilityType = VisibilityType.OPEN.toString();

    public String defaultSourcingType = SourcingType.OPEN.toString();

    public String defaultAuthorName = "TestUsername";

    public String dateFormat = "ddd MMM dd HH:mm:ss yyyy";

    public String defaultUserId = "1";

    public String defaultTitle = "Test Project";

    public String defaultSynopsis = "Test Project";

    public String defaultTagValue = "Tagged";

    public static boolean hasDefaultAuthorBeenCreated = false;

    public static int numofProjectsCreated = 0;

    public static int numOfTagsCreated = 0;

    public static int numOfPartsRequested = 0;

    public static int numOfAuthorsCreated = 0;

    public static int numOfAuthorProjectRolesCreated = 0;

    @Test
    public void testInstance() {
        assertThat(true).isTrue();
    }

    @Before
    public void setup() {
        if (!hasDefaultAuthorBeenCreated) {
            authorLifecycleListener.handleUserCreationEvent(new AccountCreationMessage(Integer.parseInt(defaultUserId), defaultAuthorName, "TestEmail@email.net"));
            hasDefaultAuthorBeenCreated = true;
            numOfAuthorsCreated++;
        }
    }

    public AuthorProjectRoleDto createAuthorRole(String userId, String projectId, AuthorProjectRoleType type) throws Exception {
        numOfAuthorProjectRolesCreated++;
        return ReadWriteUtils.asObjectFromString(AuthorProjectRoleDto.class,
                this.mockMvc.perform(post(ROLES_PATH).header("User", defaultUserId)
                        .param("userId", userId)
                        .param("projectId", projectId)
                        .param("authorProjectRoleType", type.toString())).andReturn()
                        .getResponse().getContentAsString());
    }

    public Author createNewAuthor(Integer userId, String authorName) {
        authorLifecycleListener.handleUserCreationEvent(new AccountCreationMessage(userId, authorName, "NewTestEmail@email.net"));
        numOfAuthorsCreated++;
        return authorRepository.findAuthorByUserIdEquals(userId).orElseThrow(() -> new RuntimeException("Something went wrong creating a test Author."));
    }

    public ProjectPartDto createDefaultPartOnProjectId(String projectId) throws Exception {
        numOfPartsRequested++;

        String content = this.mockMvc.perform(post(PARTS_PATH + "project/" + projectId + " /parts").header("User", defaultUserId))
                .andReturn().getResponse().getContentAsString();
        return ReadWriteUtils.asObjectFromString(ProjectPartDto.class,
                content);
    }

    public ProjectTagDto createDefaultTagOnProjectId(String projectId) throws Exception {
        numOfTagsCreated++;
        String stringValue = this.mockMvc.perform(post(PROJECTS_PATH + projectId + "/tags")
                .param("value", defaultTagValue)
                .header("User", defaultUserId)).andReturn().getResponse().getContentAsString();
        return ReadWriteUtils.asObjectFromString(ProjectTagDto.class,
                stringValue);
    }

    public ProjectDto createDefaultProject() throws Exception {

        return ReadWriteUtils.asObjectFromString(ProjectDto.class,
                createProjectWithTitleAndSynopsis(defaultTitle, defaultSynopsis)
                        .andReturn()
                        .getResponse()
                        .getContentAsString());
    }

    public ProjectDto createProjectWithTitleSynopsisAndUserId(String title, String synopsis, String userId) throws Exception {
        return ReadWriteUtils.asObjectFromString(ProjectDto.class,
                createProject(title, synopsis, userId)
                        .andReturn()
                        .getResponse()
                        .getContentAsString());
    }

    public ResultActions createProjectWithTitleAndSynopsis(String title, String synopsis) throws Exception {
        return createProject(title, synopsis, defaultUserId);
    }

    private ResultActions createProject(String title, String synopsis, String userId) throws Exception {
        numofProjectsCreated++;
        numOfAuthorProjectRolesCreated++;

        return mockMvc.perform((post(PROJECTS_PATH)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("title", title)
                .param("synopsis", synopsis)
                .param("projectType", defaultProjectType)
                .param("visibilityType", defaultVisibilityType)
                .param("sourcingType", defaultSourcingType)
                .header("User", userId));
    }

}
