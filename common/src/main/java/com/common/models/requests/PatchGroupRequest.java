package com.common.models.requests;

import com.common.models.enums.GroupType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatchGroupRequest {

    private String name;

    private String description;

    private GroupType type;
}
