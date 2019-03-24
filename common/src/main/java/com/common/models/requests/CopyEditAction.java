package com.common.models.requests;

import com.common.models.dtos.EditActionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Valid
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CopyEditAction {

    EditActionType action;
}
