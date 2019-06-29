package com.notify.controllers;

import com.common.models.dtos.NotificationDto;
import com.notify.controllers.assemblers.NotificationAssembler;
import com.notify.dao.entities.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationAssembler assembler;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, path="/{notificationId}")
    public Resource<NotificationDto> findById(@RequestHeader("User") String userid,
                                              @PathVariable("notificationId") Notification notification) {
        return new Resource(assembler.toResource(notification));
    }
}