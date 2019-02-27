// Copyright (c) 2019 Travelex Ltd

package com.common.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto extends ResourceSupport implements Identifiable<Link> {

    Integer idField;
    Integer userId;
    String username;

}
