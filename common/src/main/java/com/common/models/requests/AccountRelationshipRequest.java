package com.common.models.requests;

import com.common.models.enums.AccountRelationshipType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Valid
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountRelationshipRequest {

    @NotNull
    private AccountRelationshipType type;
}
