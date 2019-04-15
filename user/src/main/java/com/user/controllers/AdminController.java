package com.user.controllers;

import com.common.models.dtos.AccountDto;
import com.user.controllers.assemblers.AccountResourceAssembler;
import com.user.dao.entites.Account;
import com.user.dao.entites.Group;
import com.user.services.AdminActionManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/admin")
@Slf4j
public class AdminController {

    @Autowired
    private AdminActionManagementService adminActionManagementService;

    @Autowired
    private AccountResourceAssembler accountResourceAssembler;

    @Secured("ROLE_ADMIN")
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/accounts/{accountId}/ban")
    public Resource<AccountDto> banAccount(@PathVariable("accountId") Account account,
                                           @ModelAttribute("reason") String reason,
                                           @RequestHeader("User") String adminUserId) {
        log.info(">[ADMIN] {} just banned user {} for {}", adminUserId, account.getId(), reason);
        Account accountEntity = adminActionManagementService.banAccount(account, adminUserId);
        return new Resource<>(accountResourceAssembler.toResource(accountEntity));
    }

    @Secured("ROLE_ADMIN")
    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE, path = "/groups/{groupId}/delete")
    public ResponseEntity deleteGroup(@PathVariable("groupId") Group group,
                                      @RequestHeader("User") Account account) {
        log.info(">[ADMIN] {} just deleted group {} ", account.getId(), group.getId());
        adminActionManagementService.removeGroup(group, account);
        return ResponseEntity.ok().build();
    }
}
