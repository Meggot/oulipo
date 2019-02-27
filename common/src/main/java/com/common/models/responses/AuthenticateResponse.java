// Copyright (c) 2019 Travelex Ltd

package com.common.models.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AuthenticateResponse {

    private boolean isAuthenticated;
}
