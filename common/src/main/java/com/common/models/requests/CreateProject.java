// Copyright (c) 2019 Travelex Ltd

package com.common.models.requests;

import com.common.models.dtos.ProjectType;
import com.common.models.dtos.SourcingType;
import com.common.models.dtos.VisibilityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Valid
@NoArgsConstructor
@AllArgsConstructor
public class CreateProject {

    @NotNull
    @Size(min = 5, max = 20)
    private String title;

    @NotNull
    private String synopsis;

    @NotNull
    private ProjectType projectType;

    @NotNull
    private VisibilityType visibilityType;

    @NotNull
    private SourcingType sourcingType;

}
