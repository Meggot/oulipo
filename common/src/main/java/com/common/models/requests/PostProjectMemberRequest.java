package com.common.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Valid
@AllArgsConstructor
@NoArgsConstructor
public class PostProjectMemberRequest {

    @NotNull
    public Integer projectId;

    @NotNull
    public String projectName;
}
