// Copyright (c) 2019 Travelex Ltd

package com.metadata.controllers;

import com.common.models.dtos.EntityType;
import com.common.models.dtos.MatrixDto;
import com.google.common.collect.Lists;
import com.metadata.services.MatrixManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/matrix")
public class MatrixController {

    @Autowired
    MatrixManagementService matrixManagementService;

    @ResponseBody
    @RequestMapping("/{entityType}/{entityId}")
    public Resources<MatrixDto> getMatrixForEntityIds(@PathVariable("entityType") EntityType type, @PathVariable("entityId") String[] entityIds) {
        List<String> idList = Lists.newArrayList(entityIds);
        List<MatrixDto> matrixDtos = new ArrayList<>();
        for (String entityId : idList) {
            matrixDtos.add(matrixManagementService.generateMatrixForTypeAndId(type, entityId));
        }
        return new Resources<>(matrixDtos);
    }

}
