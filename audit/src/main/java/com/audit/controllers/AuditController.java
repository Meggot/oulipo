// Copyright (c) 2019 Travelex Ltd

package com.audit.controllers;

import com.audit.controllers.assemblers.AuditAssembler;
import com.audit.dao.entites.Audit;
import com.audit.dao.repository.AuditRepository;
import com.common.models.dtos.AuditDto;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping
@ExposesResourceFor(AuditDto.class)
public class AuditController {

    @Autowired
    AuditRepository auditRepository;

    @Autowired
    AuditAssembler auditAssembler;

    @ResponseBody
    @GetMapping
    public HttpEntity<PagedResources<AuditDto>> findAll(@QuerydslPredicate(root = Audit.class)Predicate predicate,
                                                        Pageable pageable,
                                                        Model model,
                                                        PagedResourcesAssembler pagedResourcesAssembler) {
        Page<AuditDto> auditDtos = auditRepository.findAll(predicate, pageable).map(resource -> auditAssembler.toResource((Audit) resource));
        model.addAttribute("audits", auditDtos);
        return new ResponseEntity<>(pagedResourcesAssembler.toResource(auditDtos), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/{auditId}")
    public Resource<AuditDto> findAuditById(@PathVariable("auditId") Audit audit, Model model) {
        AuditDto dto = auditAssembler.toResource(audit);
        model.addAttribute("audit", dto);
        return new Resource<>(dto);
    }
}
