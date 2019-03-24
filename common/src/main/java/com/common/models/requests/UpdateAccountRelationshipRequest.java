package com.common.models.requests;

import com.common.models.dtos.AccountRelationshipStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Valid
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateAccountRelationshipRequest {

    @NotNull
    public AccountRelationshipStatus status;
}
