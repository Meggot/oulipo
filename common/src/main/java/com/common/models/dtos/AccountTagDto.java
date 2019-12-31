package com.common.models.dtos;

import com.common.models.enums.AccountTagCategory;
import com.common.models.enums.TagType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Relation("accountTag")
public class AccountTagDto extends ResourceSupport implements Identifiable<Link> {

    private Integer idField;

    private AccountTagCategory category;

    private TagType type;

    private String value;

}

