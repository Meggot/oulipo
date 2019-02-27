// Copyright (c) 2019 Travelex Ltd

package com.common.models.messages;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class AccountUpdateMessage  implements Serializable {

    String accountId;

    String newUsername;

    String oldUsername;

    String newEmail;

    String oldEmail;

    String oldPassword;

    String newPassword;
}
