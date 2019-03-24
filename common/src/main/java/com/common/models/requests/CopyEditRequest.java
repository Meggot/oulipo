package com.common.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Valid
public class CopyEditRequest {
    String delta;
}
