package com.project.controllers;

import com.common.models.dtos.CopyDto;
import com.project.controllers.assemblers.CopyAssembler;
import com.project.dao.entites.Copy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/copy")
public class CopyController {

    @Autowired
    CopyAssembler copyAssembler;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, path = "/{copyId}")
    Resource<CopyDto> getCopyById(@PathVariable("copyId") Copy copy,
                                  Model model) {
        model.addAttribute("copy", copy);
        return new Resource<>(copyAssembler.toResource(copy));
    }
}
