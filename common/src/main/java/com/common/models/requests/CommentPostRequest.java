package com.common.models.requests;

import com.common.models.enums.EntityType;
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
    private Integer entityId;

    @NotNull
    private String value;

    @NotNull
    private EntityType entityType;
}
