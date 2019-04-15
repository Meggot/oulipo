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
public class AccountTagDto extends ResourceSupport implements Identifiable<Link> {

    private Integer idField;

    private AccountTagCategory category;

    private TagType type;

    private String value;

}

