package com.common.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Valid
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendMessageRequest {

    @NotNull
    public String value;
}
