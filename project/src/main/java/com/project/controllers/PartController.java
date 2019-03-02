package com.project.controllers;

import com.common.models.dtos.ProjectPartDto;
import com.project.controllers.assemblers.PartAssembler;
import com.project.controllers.assemblers.ProjectAssembler;
import com.project.dao.entites.Project;
import com.project.dao.entites.ProjectPart;
import com.project.services.PartManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/parts")
public class PartController {

    @Autowired
    PartManagementService partManagementService;

    @Autowired
    PartAssembler partAssembler;

    @ResponseBody
    @RequestMapping(path = "/project/{projectId}/parts", method = RequestMethod.POST)
    public Resource<ProjectPartDto> requestNextPart(@PathVariable("projectId") Project project,
                                                    @RequestHeader("User") String userId){
        ProjectPart projectPart = partManagementService.requestPartOnProject(project, userId);
        return new Resource<>(partAssembler.toResource(projectPart));
    }

    @ResponseBody
    @RequestMapping(path = "/{partId}", method = RequestMethod.PATCH)
    public Resource<ProjectPartDto> addValueToPart(@PathVariable("partId") ProjectPart part,
                                                   @RequestHeader("User") String userId,
                                                   @RequestBody String partValue) {
        ProjectPart projectPart = partManagementService.postValueOnPart(part, userId, partValue);
        return new Resource<>(partAssembler.toResource(projectPart));
    }
}
