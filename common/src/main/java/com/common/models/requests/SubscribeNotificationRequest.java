package com.common.models.requests;

import com.common.models.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Valid
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscribeNotificationRequest {

    @NotNull
    Integer entityId;

    @NotNull
    @NotEmpty
    List<NotificationType> types;
}
