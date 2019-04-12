package com.common.models.requests;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Valid
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendMessageRequest {

    @NotNull
    public String value;
}
