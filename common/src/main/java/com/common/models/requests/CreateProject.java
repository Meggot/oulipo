// Copyright (c) 2019 Travelex Ltd

package com.common.models.requests;

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

    private String synopsis;
}
