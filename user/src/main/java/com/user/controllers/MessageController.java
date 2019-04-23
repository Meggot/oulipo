package com.user.controllers;

import com.common.models.dtos.MessageDto;
import com.common.models.requests.SendMessageRequest;
import com.querydsl.core.types.Predicate;
import com.user.controllers.assemblers.MessageAssembler;
import com.user.dao.entites.Account;
import com.user.dao.entites.Message;
import com.user.dao.repository.AccountRepository;
import com.user.dao.repository.MessageRepository;
import com.user.services.MessageManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping(produces = "application/json", path = "/messages")
public class MessageController {

    @Autowired
    private MessageAssembler assembler;

    @Autowired
    private MessageRepository repository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MessageManagementService messageManagementService;

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(method = RequestMethod.GET)
    public PagedResources<MessageDto> getMessages(@QuerydslPredicate(root = Message.class) Predicate predicate,
                                                  Pageable pageable,
                                                  PagedResourcesAssembler pagedResourcesAssembler) {
        Page<MessageDto> messageDtoPage = repository.findAll(predicate, pageable).map(assembler::toResource);
        return pagedResourcesAssembler.toResource(messageDtoPage);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/user/{username}/message")
    public Resource<MessageDto> postMessageOnUserName(@PathVariable("username") String username,
                                                    @RequestHeader("User") Account sender,
                                                    @ModelAttribute @Valid SendMessageRequest message) {
        Account recipient = accountRepository.getAccountByUsername(username).orElseThrow(
                () -> new NoSuchElementException("User " + sender.getUsername() + " tried to send a message to " + username +" but an account doesn't exist with that username")
        );
        Message savedMessage = messageManagementService.handlePostMessage(message, recipient, sender);
        return new Resource<>(assembler.toResource(savedMessage));
    }

}
