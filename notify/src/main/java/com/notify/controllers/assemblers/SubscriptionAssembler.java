package com.notify.controllers.assemblers;

import com.common.models.dtos.SubscriptionDto;
import com.notify.controllers.SubscriptionController;
import com.notify.dao.entities.Subscription;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionAssembler extends ResourceAssemblerSupport<Subscription, SubscriptionDto> {

    public SubscriptionAssembler() {
        super(SubscriptionController.class, SubscriptionDto.class);
    }


    @Override
    public SubscriptionDto toResource(Subscription subscription) {
        SubscriptionDto dto = createResourceWithId(subscription.getId(), subscription);
        dto.setEntityId(subscription.getEntityId());
        dto.setIdField(subscription.getId());
        dto.setNotificationType(subscription.getNotificationType());
        dto.setPostboxId(subscription.getPostbox().getId());
        return dto;
    }
}
