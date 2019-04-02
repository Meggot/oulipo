package com.user.controllers;

import com.common.models.dtos.AccountTagDto;
import com.user.controllers.assemblers.AccountTagAssembler;
import com.user.dao.entites.Account;
import com.user.dao.entites.AccountTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/tags")
public class AccountTagController {

    @Autowired
    AccountTagAssembler accountTagAssembler;

    @RequestMapping(method = RequestMethod.GET, path = "/{tagId}")
    @ResponseBody
    public Resource<AccountTagDto> findById(@PathVariable("tagId") AccountTag tag,
                                            @RequestHeader("User") Account account) {
        return new Resource<>(accountTagAssembler.toResource(tag));
    }
}
