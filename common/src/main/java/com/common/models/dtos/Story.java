// Copyright (c) 2019 Travelex Ltd

package com.common.models.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Story {
    private String storyTitle;
    private String storySynopsis;
    private Integer projectId;
    private String fullStory;
}
