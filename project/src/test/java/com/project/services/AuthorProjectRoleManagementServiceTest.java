package com.project.services;

import com.common.models.dtos.AuthorProjectRoleType;
import com.common.models.requests.AuthorProjectRoleRequest;
import com.common.models.requests.UpdateAuthorProjectRole;
import com.project.dao.entites.Author;
import com.project.dao.entites.AuthorProjectRole;
import com.project.dao.entites.Project;
import com.project.dao.repository.AuthorProjectRoleRepository;
import com.project.dao.repository.AuthorRepository;
import com.project.dao.repository.ProjectRepository;
import com.project.services.permissions.AuthorRolePermissions;
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

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(JUnit4.class)
public class AuthorProjectRoleManagementServiceTest {

    @Mock
    private AuthorProjectRoleRepository authorProjectRoleRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private AuthorRolePermissions authorRolePermissions;

    private AuthorProjectRoleManagementService authorProjectRoleManagementService;

    private Author actedAuthor = new Author();

    private Author actorAuthor = new Author();

    private Project project = new Project();

    private Integer projectId = 1;

    private Integer actedUserId = 2;

    private Integer actorUserId = 3;

    private Integer roleId = 4;

    private AuthorProjectRole existingRole;

    private AuthorProjectRole actorProjectRole = new AuthorProjectRole();

    @Captor
    private ArgumentCaptor<AuthorProjectRole> authorRepositoryCaptor;

    private AuthorProjectRoleRequest authorProjectRoleRequest;
    private UpdateAuthorProjectRole updateAuthorProjectRole;

    @Before
    public void setup() {
        initMocks(this);
        authorProjectRoleManagementService = new AuthorProjectRoleManagementService(authorProjectRoleRepository,
                authorRepository,
                projectRepository,
                authorRolePermissions);
        actedAuthor.setUserId(actedUserId);
        actorAuthor.setUserId(actorUserId);
        project.setId(projectId);
        actorProjectRole.setRole(AuthorProjectRoleType.CREATOR);
        actorAuthor.addAuthorProjectRole(actorProjectRole);
        project.addAuthorProjectRole(actorProjectRole);
        when(authorRepository.findAuthorByUserIdEquals(actorUserId)).thenReturn(Optional.of(actorAuthor));
        when(authorRepository.findAuthorByUserIdEquals(actedUserId)).thenReturn(Optional.of(actedAuthor));
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(authorRolePermissions.canUserRolePostRole(any(), any())).thenReturn(true);
        when(authorRolePermissions.canUserRolePatchRole(any(), any(), any())).thenReturn(true);
        authorProjectRoleRequest = new AuthorProjectRoleRequest();
        authorProjectRoleRequest.setAuthorProjectRoleType(AuthorProjectRoleType.CONTRIBUTOR);
        authorProjectRoleRequest.setProjectId(projectId);
        authorProjectRoleRequest.setUserId(actedUserId);
        existingRole = new AuthorProjectRole();
        existingRole.setRole(AuthorProjectRoleType.CONTRIBUTOR);
        existingRole.setAuthor(actedAuthor);
        existingRole.setProject(project);
        existingRole.setId(roleId);
        updateAuthorProjectRole = new UpdateAuthorProjectRole();
        updateAuthorProjectRole.setNewRole(AuthorProjectRoleType.BARRED);
        when(authorProjectRoleRepository.findById(roleId)).thenReturn(Optional.of(existingRole));
    }

    @Test
    public void test_handle_create_author_project_role_request_success() {
        authorProjectRoleManagementService.handleCreateAuthorProjectRoleRequest(authorProjectRoleRequest, actorUserId.toString());
        verify(authorProjectRoleRepository).save(authorRepositoryCaptor.capture());
        assertThat(authorRepositoryCaptor.getValue().getRole()).isEqualTo(AuthorProjectRoleType.CONTRIBUTOR);
    }

    @Test(expected = NoSuchElementException.class)
    public void test_handle_create_author_project_role_request_no_acted_author() {
        when(authorRepository.findAuthorByUserIdEquals(actedUserId)).thenReturn(Optional.empty());
        authorProjectRoleManagementService.handleCreateAuthorProjectRoleRequest(authorProjectRoleRequest, actorUserId.toString());
    }

    @Test(expected = NoSuchElementException.class)
    public void test_handle_create_author_project_role_request_no_actor_author() {
        when(authorRepository.findAuthorByUserIdEquals(actorUserId)).thenReturn(Optional.empty());
        authorProjectRoleManagementService.handleCreateAuthorProjectRoleRequest(authorProjectRoleRequest, actorUserId.toString());
    }

    @Test(expected = NoSuchElementException.class)
    public void test_handle_create_author_project_role_request_no_project() {
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());
        authorProjectRoleManagementService.handleCreateAuthorProjectRoleRequest(authorProjectRoleRequest, actorUserId.toString());
    }

    @Test(expected = RuntimeException.class)
    public void test_handle_create_author_project_role_request_existing_role() {
        project.addAuthorProjectRole(existingRole);
        authorProjectRoleManagementService.handleCreateAuthorProjectRoleRequest(authorProjectRoleRequest, actorUserId.toString());
    }

    @Test(expected = RuntimeException.class)
    public void test_handle_create_author_project_role_request_actor_no_role() {
        actorAuthor.setAuthorProjectRoles(Lists.emptyList());
        authorProjectRoleManagementService.handleCreateAuthorProjectRoleRequest(authorProjectRoleRequest, actorUserId.toString());
    }

    @Test
    public void test_handle_patch_author_project_role_request_success() {
        authorProjectRoleManagementService.handleUpdateAuthorProjectRoleRequest(existingRole, updateAuthorProjectRole, actorUserId.toString());
        verify(authorProjectRoleRepository).save(authorRepositoryCaptor.capture());
        assertThat(authorRepositoryCaptor.getValue().getRole()).isEqualTo(AuthorProjectRoleType.BARRED);
    }

    @Test(expected = NoSuchElementException.class)
    public void test_handle_patch_author_project_role_request_no_actor_author() {
        when(authorRepository.findAuthorByUserIdEquals(actorUserId)).thenReturn(Optional.empty());
        authorProjectRoleManagementService.handleUpdateAuthorProjectRoleRequest(existingRole, updateAuthorProjectRole, actorUserId.toString());
    }

    @Test(expected = RuntimeException.class)
    public void test_handle_patch_author_project_role_request_actor_no_role() {
        actorAuthor.setAuthorProjectRoles(Lists.emptyList());
        authorProjectRoleManagementService.handleUpdateAuthorProjectRoleRequest(existingRole, updateAuthorProjectRole, actorUserId.toString());
    }
}