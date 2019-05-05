// Copyright (c) 2019 Travelex Ltd

package com.common.models.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectUpdateMessage implements Serializable {

    private String oldTitle;

    private String oldSynopsis;

    private String newTitle;

    private String newSynopsis;

    private String UserId;

    private Integer projectId;
}
