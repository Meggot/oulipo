package com.user.controllers;

import com.common.models.dtos.AccountDto;
import com.user.controllers.assemblers.AccountResourceAssembler;
import com.user.dao.entites.Account;
import com.user.services.AdminActionManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(path = "/admin")
@Slf4j
public class AdminController {

    @Autowired
    private AdminActionManagementService adminActionManagementService;

    @Autowired
    private AccountResourceAssembler accountResourceAssembler;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/accounts/{accountId}/ban")
    public Resource<AccountDto> banAccount(@PathVariable("accountId") Account account,
                                           @ModelAttribute @NotNull String reason,
                                           @RequestHeader("User") String adminUserId) {
        log.info(">[ADMIN] {} just banned user {} for {}", adminUserId, account.getId(), reason);
        Account accountEntity = adminActionManagementService.banAccount(account, adminUserId);
        return new Resource<>(accountResourceAssembler.toResource(accountEntity));
    }
}
