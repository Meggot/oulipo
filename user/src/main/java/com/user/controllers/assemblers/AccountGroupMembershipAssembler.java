package com.user.controllers.assemblers;

import com.common.models.dtos.AccountGroupMembershipDto;
import com.user.controllers.GroupsController;
import com.user.dao.entites.AccountGroupMembership;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class AccountGroupMembershipAssembler extends ResourceAssemblerSupport<AccountGroupMembership, AccountGroupMembershipDto> {

    public AccountGroupMembershipAssembler() {
        super(GroupsController.class, AccountGroupMembershipDto.class);
    }

    @Override
    public AccountGroupMembershipDto toResource(AccountGroupMembership accountGroupMembership) {
        AccountGroupMembershipDto dto = createResourceWithId(accountGroupMembership.getId(), accountGroupMembership);
        dto.setAccountId(accountGroupMembership.getAccount().getId());
        dto.setAccountUsername(accountGroupMembership.getAccount().getUsername());
        if (accountGroupMembership.getAddedBy() != null) {
            dto.setAddedById(accountGroupMembership.getAddedBy().getId());
            dto.setAddedByUsername(accountGroupMembership.getAddedBy().getUsername());
        }
        dto.setGroupId(accountGroupMembership.getGroup().getId());
        dto.setGroupName(accountGroupMembership.getGroup().getName());
        dto.setIdField(accountGroupMembership.getId());
        dto.setRole(accountGroupMembership.getRole());
        dto.setAdded(accountGroupMembership.getCreationDate());
        return dto;
    }
}