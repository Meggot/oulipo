package com.notify.controllers;

import com.common.models.dtos.NotificationDto;
import com.notify.controllers.assemblers.SubscriptionAssembler;
import com.notify.dao.entities.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(path = "/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionAssembler assembler;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, path="/{subscriptionId}")
    public Resource<NotificationDto> findById(@RequestHeader("User") String userid,
                                              @PathVariable("subscriptionId") Subscription subscription) {
        return new Resource(assembler.toResource(subscription));
    }

}
