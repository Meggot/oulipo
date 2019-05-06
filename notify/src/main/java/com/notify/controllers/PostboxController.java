package com.notify.controllers;

import com.common.models.dtos.PostBoxDto;
import com.common.models.dtos.SubscriptionDto;
import com.common.models.requests.SubscribeNotificationRequest;
import com.notify.controllers.assemblers.PostboxAssembler;
import com.notify.controllers.assemblers.SubscriptionAssembler;
import com.notify.dao.entities.Postbox;
import com.notify.services.PostboxManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/postbox")
public class PostboxController {

    @Autowired
    private PostboxAssembler postboxAssembler;

    @Autowired
    private SubscriptionAssembler subscriptionAssembler;

    @Autowired
    private  PostboxManagementService postboxManagementService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Resource<PostBoxDto> getMyPost(@RequestHeader("User") String userId) {
        Postbox postBox = postboxManagementService.handleGetMyPost(Integer.parseInt(userId));
        final PostBoxDto dto = postboxAssembler.toResource(postBox);
        postboxManagementService.readMail(postBox);
        return new Resource(dto);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/subscribe")
    public Resources<SubscriptionDto> subscribeOnPostBox(@RequestHeader("User") String userId,
                                                         @ModelAttribute @Valid SubscribeNotificationRequest request) {
        Postbox postBox = postboxManagementService.handleGetMyPost(Integer.parseInt(userId));
        List<SubscriptionDto> subscription = postboxManagementService.handleSubscribe(postBox, request, userId)
                .stream()
                .map(subscriptionAssembler::toResource)
                .collect(Collectors.toList());
        return new Resources<>(subscription);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/{postBoxId}/subscribe")
    public Resources<SubscriptionDto> subscribeOnPostBox(@RequestHeader("User") String userId,
                                                         @PathVariable("postBoxId") Postbox postbox,
                                                         @ModelAttribute @Valid SubscribeNotificationRequest request) {
        List<SubscriptionDto> subscription = postboxManagementService.handleSubscribe(postbox, request, userId)
                .stream()
                .map(subscriptionAssembler::toResource)
                .collect(Collectors.toList());
        return new Resources<>(subscription);
    }

    //Doesn't read the postbox, good for debugging.
    @ResponseBody
    @RequestMapping(path="/{postBoxId}", method = RequestMethod.GET)
    public Resource<PostBoxDto> getBoxById(@RequestHeader("User") String userid,
                                           @PathVariable("postBoxId") Postbox postBox) {
        return new Resource(postboxAssembler.toResource(postBox));
    }


}
