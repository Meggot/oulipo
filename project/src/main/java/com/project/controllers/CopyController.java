package com.project.controllers;

import com.common.models.dtos.CopyDto;
import com.common.models.dtos.CopyEditDto;
import com.common.models.requests.CopyEditRequest;
import com.project.controllers.assemblers.CopyAssembler;
import com.project.controllers.assemblers.CopyEditAssembler;
import com.project.dao.entites.Copy;
import com.project.dao.entites.CopyEdit;
import com.project.services.CopyEditManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/copy")
public class CopyController {

    @Autowired
    CopyAssembler copyAssembler;

    @Autowired
    CopyEditManagementService copyEditManagementService;

    @Autowired
    CopyEditAssembler copyEditAssembler;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, path = "/{copyId}")
    public Resource<CopyDto> getCopyById(@PathVariable("copyId") Copy copy,
                                         Model model) {
        model.addAttribute("copy", copy);
        return new Resource<>(copyAssembler.toResource(copy));
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/{copyId}/edits")
    public Resource<CopyEditDto> addCopyEditToCopy(@PathVariable("copyId") Copy copy,
                                                   @ModelAttribute @Valid CopyEditRequest copyEditRequest,
                                                   @RequestHeader("User") String userId) {
        CopyEdit copyEdit = copyEditManagementService.handlePostCopyEdit(copyEditRequest, copy, userId);
        return new Resource<>(copyEditAssembler.toResource(copyEdit));
    }
}
