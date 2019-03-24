package com.project.controllers;

import com.common.models.dtos.CopyEditDto;
import com.common.models.requests.CopyEditAction;
import com.project.controllers.assemblers.CopyEditAssembler;
import com.project.dao.entites.CopyEdit;
import com.project.dao.repository.CopyEditRepository;
import com.project.services.CopyEditManagementService;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/edits")
public class CopyEditController {

    @Autowired
    private CopyEditAssembler copyEditAssembler;

    @Autowired
    private CopyEditRepository copyEditRepository;

    @Autowired
    private CopyEditManagementService copyEditManagementService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, path = "/{editId}")
    public Resource<CopyEditDto> getEditById(@PathVariable("editId") CopyEdit copyEdit,
                                             @RequestHeader("User") String userId) {
        return new Resource<>(copyEditAssembler.toResource(copyEdit));
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public PagedResources<CopyEditDto> getAll(@QuerydslPredicate(root = CopyEdit.class) Predicate predicate,
                                              @RequestHeader("User") String userId,
                                              Pageable pageable, PagedResourcesAssembler pagedResourcesAssembler) {
        Page<CopyEditDto> copyEditPage = copyEditRepository.findAll(predicate, pageable).map(copyEdit -> copyEditAssembler.toResource(copyEdit));
        return pagedResourcesAssembler.toResource(copyEditPage);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PATCH, path = "/{editId}/action")
    public Resource<CopyEditDto> action(@PathVariable("editId") CopyEdit copyEdit,
                                        @RequestHeader("User") String userId,
                                        @ModelAttribute @Valid CopyEditAction copyEditAction) {
        CopyEdit actionedCopy = copyEditManagementService.actionAgainstCopyEdit(copyEditAction.getAction(), userId, copyEdit);
        return new Resource<>(copyEditAssembler.toResource(actionedCopy));
    }
}
