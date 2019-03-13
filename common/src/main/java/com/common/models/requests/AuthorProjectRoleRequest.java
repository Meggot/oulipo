package com.common.models.requests;

import com.common.models.dtos.AuthorProjectRoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class AuthorProjectRoleRequest {

    @NotNull
    private Integer userId;

    @NotNull
    private Integer projectId;

    @NotNull
    private AuthorProjectRoleType authorProjectRoleType;
}
