package com.common.models.requests;

import com.common.models.dtos.AccountTagCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Valid
@NoArgsConstructor
@AllArgsConstructor
public class AccountTagRequest {

    @NotNull
    public String value;

    public AccountTagCategory category;

}
