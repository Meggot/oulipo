// Copyright (c) 2019 Travelex Ltd

package com.project.dao.entites;

import com.common.models.dtos.PartStatus;

import java.util.UUID;
import javax.persistence.Id;

public class ProjectPart extends EntityObject {

    @Id
    private UUID id;

    private Integer sequence;

    private UUID holdingAuthorId;

    private PartStatus status;

    private String value;

}
