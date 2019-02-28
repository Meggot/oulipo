// Copyright (c) 2019 Travelex Ltd

package com.project.services;

import com.common.models.exceptions.InternalServerException;
import com.project.dao.entites.Author;
import com.project.dao.repository.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthorManagementService {

    @Autowired
    AuthorRepository authorRepository;

    public void createAuthor(Integer userId, String username) {
        if (authorRepository.existsByUserIdEquals(userId)) {
            log.error("Failed to create an Author with userId {} as it already exists.", userId);
            throw new InternalServerException("Author already exists for user_id " + userId);
        }
        Author newAuthor = new Author();
        newAuthor.setUserId(userId);
        newAuthor.setUsername(username);
        newAuthor = authorRepository.save(newAuthor);
        log.info(">[CREATE] createAuthor({}, {}) Created author successfully, author id {}", userId, username, newAuthor.getAuthorId());
    }

    public void updateAuthor(Integer userId, String newUsername) {
        if (!authorRepository.existsByUserIdEquals(userId)) {
            log.error("Tried to update an author where the User id {} does not exist", userId);
            throw new InternalServerException("Author does not exist with userId " + userId);
        }
        Author author = authorRepository.findAuthorByUserIdEquals(userId).get();
        author.setUsername(newUsername);
        authorRepository.save(author);
    }
}
