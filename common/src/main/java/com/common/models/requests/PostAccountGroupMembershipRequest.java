package com.common.models.requests;

import com.common.models.dtos.GroupRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Valid
@AllArgsConstructor
@NoArgsConstructor
public class PostAccountGroupMembershipRequest {

    @NotNull
    public GroupRole role;

}
