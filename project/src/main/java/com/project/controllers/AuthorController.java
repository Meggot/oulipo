package com.project.controllers;

import com.common.models.dtos.AuthorDto;
import com.project.controllers.assemblers.AuthorAssembler;
import com.project.dao.entites.Author;
import com.project.dao.repository.AuthorRepository;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "/authors")
public class AuthorController {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    AuthorAssembler authorAssembler;


    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    PagedResources<Resource<AuthorDto>> getAuthors(@RequestHeader("User") String userId,
                                         @QuerydslPredicate(root = Author.class) Predicate predicate,
                                         Pageable pageable, Model model, PagedResourcesAssembler<AuthorDto> pagedResourcesAssembler) {
        Page<AuthorDto> page = authorRepository.findAll(predicate, pageable).map(author -> authorAssembler.toResource(author));
        model.addAttribute("authors", page);
        return pagedResourcesAssembler.toResource(page);
    }


    @ResponseBody
    @RequestMapping(path = "/thisAuthor", method = RequestMethod.GET)
    Resource<AuthorDto> getThisAuthor(@RequestHeader("User") String userId) {
        return new Resource<>(authorAssembler.toResource(authorRepository.findAuthorByUserIdEquals(Integer.parseInt(userId))
                .orElseThrow(() -> new NoSuchElementException("Author with UserId " + userId + " not found."))));
    }

    @ResponseBody
    @RequestMapping(path = "/{userId}", method = RequestMethod.GET)
    Resource<AuthorDto> getAuthorById(@RequestHeader("User") String userId,
                                      @PathVariable("userId") Integer requestedAuthorUserId) {
        return new Resource<>(authorAssembler.toResource(authorRepository.findAuthorByUserIdEquals(requestedAuthorUserId)
                .orElseThrow(() -> new NoSuchElementException("Author with UserId " + userId + " not found."))));
    }
}
