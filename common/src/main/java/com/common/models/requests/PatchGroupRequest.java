package com.common.models.requests;

import com.common.models.dtos.GroupType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatchGroupRequest {

    private String name;

    private String description;

    private GroupType type;
}
