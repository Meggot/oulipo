// Copyright (c) 2019 Travelex Ltd

package com.common.models.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartValueDto extends ResourceSupport implements Identifiable<Link> {

    Integer idField;
    String value;
    //TODO: fix this userId, this shouldn't be passed in OBJ req and should be Author ID in response.
    Integer userId;
    Integer version;
    LocalDateTime creationDate;
    PartStatus patchedPartStatus;

}
