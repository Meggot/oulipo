package com.common.models.requests;

import com.common.models.dtos.GroupType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Valid
@AllArgsConstructor
@NoArgsConstructor
public class PostGroupRequest {

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private GroupType type;

}
