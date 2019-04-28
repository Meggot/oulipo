package com.common.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Valid
@NoArgsConstructor
@AllArgsConstructor
public class CreateTagRequest {
    @NotNull
    private String value;
}
