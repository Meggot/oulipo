// Copyright (c) 2019 Travelex Ltd

package com.common.models.requests;

import com.common.models.dtos.PartStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Valid
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostPartValueRequest {

    @NotNull
    private String value;

    @NotNull
    private PartStatus reviewStatus;

}
