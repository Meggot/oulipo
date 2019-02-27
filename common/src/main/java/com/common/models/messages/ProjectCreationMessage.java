// Copyright (c) 2019 Travelex Ltd

package com.common.models.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectCreationMessage implements Serializable {

    private String title;

    private String synopsis;

    private String userId;

    private String projectId;
}
