// Copyright (c) 2019 Travelex Ltd

package com.common.models.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreationMessage {

    Integer accountId;

    String username;

    String email;

}
