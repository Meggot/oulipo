package com.user.services;

import com.common.models.dtos.TagType;
import com.common.models.requests.AccountTagRequest;
import com.user.dao.entites.Account;
import com.user.dao.entites.AccountTag;
import com.user.dao.repository.AccountTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountTagManagementService {

    @Autowired
    private AccountTagRepository accountTagRepository;

    public AccountTag handleAccountTagRequest(AccountTagRequest request, Account account) {
        AccountTag accountTag = new AccountTag();
        accountTag.setCategory(request.getCategory());
        accountTag.setType(TagType.USER_ADDED);
        accountTag.setValue(request.getValue());
        account.addTag(accountTag);
        return accountTagRepository.save(accountTag);
    }
}
