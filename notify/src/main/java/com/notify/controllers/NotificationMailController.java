package com.notify.controllers;

import com.common.models.dtos.NotificationDto;
import com.notify.controllers.assemblers.NotificationMailAssembler;
import com.notify.dao.entities.NotificationMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/mail")
public class NotificationMailController {

    @Autowired
    NotificationMailAssembler assembler;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, path="/{notificationMailId}")
    public Resource<NotificationDto> findById(@RequestHeader("User") String userid,
                                              @PathVariable("notificationMailId") NotificationMail notificationMail) {
        return new Resource(assembler.toResource(notificationMail));
    }
}
