package com.common.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class CommentPostRequest {

    @NotNull
    Integer entityId;

    @NotNull
    String value;
}
