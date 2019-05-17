package com.notify.controllers;

import com.common.models.dtos.SubscriptionDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class SubscriptionList {
    public List<SubscriptionDto> content;
}
