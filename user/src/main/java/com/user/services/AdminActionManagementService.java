package com.user.services;

import com.common.models.dtos.AccountStatus;
import com.user.dao.entites.Account;
import com.user.dao.entites.Group;
import com.user.dao.repository.AccountRepository;
import com.user.dao.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminActionManagementService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private GroupRepository groupRepository;

    public Account banAccount(Account account, String userId) {
        if (account.getId() == Integer.valueOf(userId)) {
            throw new RuntimeException("You can't ban yourself!");
        }
        account.setStatus(AccountStatus.BANNED);
        return accountRepository.save(account);
    }

    public boolean removeGroup(Group group, Account admin) {
        groupRepository.delete(group);
        return true;
    }
}
