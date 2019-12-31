package com.common.models.requests;

import com.common.models.enums.AuthorProjectRoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class UpdateAuthorProjectRole {

    @NotNull
    AuthorProjectRoleType newRole;
}
