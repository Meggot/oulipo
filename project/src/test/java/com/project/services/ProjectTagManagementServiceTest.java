package com.project.services;

import com.common.models.dtos.AuthorProjectRoleType;
import com.common.models.dtos.TagType;
import com.common.models.exceptions.UnauthorizedException;
import com.common.models.requests.CreateTagRequest;
import com.project.dao.entites.Author;
import com.project.dao.entites.AuthorProjectRole;
import com.project.dao.entites.Project;
import com.project.dao.entites.ProjectTag;
import com.project.dao.repository.AuthorProjectRoleRepository;
import com.project.dao.repository.AuthorRepository;
import com.project.dao.repository.ProjectRepository;
import com.project.dao.repository.ProjectTagRepository;
import com.project.streaming.InMemoryLifecycleStreamer;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(JUnit4.class)
public class ProjectTagManagementServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private ProjectTagRepository projectTagRepository;

    @Mock
    private AuthorProjectRoleRepository authorProjectRoleRepository;

    @Mock
    private ProjectRepository projectRepository;

    private Author author;

    private Project project;

    private AuthorProjectRole authorProjectRole;

    private Integer authorId = 1;

    private Integer projectId = 2;

    private Integer authorProjectRoleId = 3;

    private CreateTagRequest createTagRequest;

    private ProjectTag deleteProjectTag;

    private String requestingUserId = "4";

    private String testTag = "TEST_TAG";

    @Captor
    private ArgumentCaptor<ProjectTag> projectTagArgumentCaptor;

    private ProjectTagManagementService projectTagManagementService;


    @Before
    public void setup() {
        initMocks(this);
        author = new Author();
        author.setAuthorId(authorId);
        author.setUserId(Integer.parseInt(requestingUserId));
        authorProjectRole = new AuthorProjectRole();
        authorProjectRole.setId(authorProjectRoleId);
        authorProjectRole.setAuthor(author);
        authorProjectRole.setRole(AuthorProjectRoleType.CREATOR);
        project = new Project();
        project.setId(projectId);
        project.setAuthorProjectRoles(Lists.newArrayList(authorProjectRole));
        author.setAuthorProjectRoles(Lists.newArrayList(authorProjectRole));
        authorProjectRole.setProject(project);

        createTagRequest = new CreateTagRequest();
        createTagRequest.setValue(testTag);

        deleteProjectTag = new ProjectTag();
        deleteProjectTag.setProject(project);
        deleteProjectTag.setOrigin(author);

        ProjectTag createdTag = new ProjectTag();
        createdTag.setId(3);
        when(authorRepository.findAuthorByUserIdEquals(Integer.parseInt(requestingUserId))).thenReturn(Optional.of(author));
        when(authorProjectRoleRepository.findById(authorProjectRoleId)).thenReturn(Optional.of(authorProjectRole));
        when(projectTagRepository.save(any())).thenReturn(createdTag);
        projectTagManagementService = new ProjectTagManagementService(authorRepository, projectTagRepository, projectRepository);
    }

    @Test
    public void handle_create_tag_create_success() {
        projectTagManagementService.handleCreateTagRequest(createTagRequest, project, requestingUserId);
        verify(projectTagRepository, times(1)).save(projectTagArgumentCaptor.capture());
        ProjectTag createdProjectTag = projectTagArgumentCaptor.getValue();
        assertThat(createdProjectTag.getOrigin()).isEqualToComparingFieldByField(author);
        assertThat(createdProjectTag.getProject()).isEqualToComparingFieldByField(project);
        assertThat(createdProjectTag.getValue()).isEqualTo(testTag);
        assertThat(createdProjectTag.getType()).isEqualTo(TagType.USER_ADDED);
    }

    @Test
    public void handle_create_system_tag() {
        Integer projectId = 32;
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        projectTagManagementService.handleSystemAddTag(createTagRequest, projectId);
        verify(projectTagRepository, times(1)).save(projectTagArgumentCaptor.capture());
        ProjectTag createdProjectTag = projectTagArgumentCaptor.getValue();
        assertThat(createdProjectTag.getProject()).isEqualToComparingFieldByField(project);
        assertThat(createdProjectTag.getValue()).isEqualTo(testTag);
        assertThat(createdProjectTag.getType()).isEqualTo(TagType.SYSTEM_ADDED);
    }

    @Test(expected = NoSuchElementException.class)
    public void handle_create_tag_create_no_author() {
        when(authorRepository.findAuthorByUserIdEquals(Integer.parseInt(requestingUserId))).thenReturn(Optional.empty());
        projectTagManagementService.handleCreateTagRequest(createTagRequest, project, requestingUserId);
    }

    @Test(expected = UnauthorizedException.class)
    public void handle_create_tag_no_author_role() {
        project.setAuthorProjectRoles(Lists.emptyList());
        projectTagManagementService.handleCreateTagRequest(createTagRequest, project, requestingUserId);
    }

    @Test(expected = UnauthorizedException.class)
    public void handle_create_tag_author_bad_role() {
        authorProjectRole.setRole(AuthorProjectRoleType.CONTRIBUTOR);
        projectTagManagementService.handleCreateTagRequest(createTagRequest, project, requestingUserId);
    }

    @Test
    public void handle_delete_tag_request() {
        projectTagManagementService.handleDeleteTagRequest(deleteProjectTag, requestingUserId);
        verify(projectTagRepository, times(1)).delete(projectTagArgumentCaptor.capture());
        ProjectTag tag = projectTagArgumentCaptor.getValue();
        assertThat(tag).isEqualToComparingFieldByField(deleteProjectTag);
    }

    @Test(expected = NoSuchElementException.class)
    public void handle_delete_tag_no_author() {
        when(authorRepository.findAuthorByUserIdEquals(Integer.parseInt(requestingUserId))).thenReturn(Optional.empty());
        projectTagManagementService.handleDeleteTagRequest(deleteProjectTag, requestingUserId);
    }

    @Test(expected = UnauthorizedException.class)
    public void handle_no_role_on_author() {
        author.setAuthorProjectRoles(Lists.emptyList());
        projectTagManagementService.handleDeleteTagRequest(deleteProjectTag, requestingUserId);
    }
}