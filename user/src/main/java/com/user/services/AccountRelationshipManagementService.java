package com.user.services;

import com.common.models.exceptions.UnauthorizedException;
import com.common.models.requests.AccountRelationshipRequest;
import com.common.models.dtos.AccountRelationshipStatus;
import com.common.models.requests.UpdateAccountRelationshipRequest;
import com.user.dao.entites.Account;
import com.user.dao.entites.AccountRelationship;
import com.user.dao.repository.AccountRelationshipRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class AccountRelationshipManagementService {

    @Autowired
    AccountRelationshipRepository relationshipRepository;


    public AccountRelationship postRelationshipRequest(AccountRelationshipRequest req, Account addedBy, Account added) {
        Optional<AccountRelationship> optional = added.getRelationships().stream()
                .filter(accountRelationship -> accountRelationship.getAdded().getId() == added.getId()
                || accountRelationship.getAddedBy().getId() == addedBy.getId())
                .findFirst();
        if (optional.isPresent()) {
            log.info(">[DELETE] {} has posted a relationship req on {} of {}, but they already had a relationship of {}, deleting old.",
                    addedBy.getUsername(), added.getUsername(), req.getType(), optional.get().getRelationshipType() + ":" + optional.get().getStatus());
            relationshipRepository.delete(optional.get());
        }
        log.info(">[CREATE] Successfully posted the status. {}", req);
        AccountRelationship accountRelationship = new AccountRelationship();
        accountRelationship.setRelationshipType(req.getType());
        accountRelationship.setStatus(AccountRelationshipStatus.REQUESTED);
        added.addRelationshipAdded(accountRelationship);
        addedBy.addRelationshipAddedBy(accountRelationship);
        return relationshipRepository.save(accountRelationship);
    }

    public AccountRelationship patchRelationship(AccountRelationship relationship, Account initiator, UpdateAccountRelationshipRequest request) {
        if (relationship.getStatus() == AccountRelationshipStatus.REQUESTED) {
            if (initiator.equals(relationship.getAddedBy())) {
                throw new UnauthorizedException("You can't patch a relationship request as the initiator");
            }
        }
        if (request.getStatus().equals(AccountRelationshipStatus.REQUESTED)) {
            throw new IllegalStateException("You can't patch a status to requested");
        }
        log.info("Successfully updated the status. {}", request);
        relationship.setStatus(request.getStatus());
        return relationshipRepository.save(relationship);
    }
}
