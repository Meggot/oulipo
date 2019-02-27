// Copyright (c) 2019 Travelex Ltd

package com.common.models.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponse {

    private String sessionToken;

}
