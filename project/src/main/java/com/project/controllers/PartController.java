package com.project.controllers;

import com.common.models.dtos.ProjectPartDto;
import com.common.models.requests.PostPartValueRequest;
import com.project.controllers.assemblers.PartAssembler;
import com.project.dao.entites.Project;
import com.project.dao.entites.ProjectPart;
import com.project.services.PartManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/parts", produces = "application/json")
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
                                                   @ModelAttribute("PostPartValueRequest") PostPartValueRequest partValue) {
        ProjectPart projectPart = partManagementService.postValueOnPart(part, userId, partValue);
        return new Resource<>(partAssembler.toResource(projectPart));
    }


    @ResponseBody
    @RequestMapping(path = "/{partId}", method = RequestMethod.GET)
    public Resource<ProjectPartDto> getPartById(@PathVariable("partId") ProjectPart part) {
        return new Resource<>(partAssembler.toResource(part));
    }

    @ResponseBody
    @RequestMapping(path = "/{partId}/delete", method = RequestMethod.PATCH)
    public ResponseEntity deletePartId(@PathVariable("partId") ProjectPart part,
                                       @RequestHeader("User") String userId) {
        boolean partRemoved = partManagementService.deletePart(part, userId);
        if (partRemoved) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
