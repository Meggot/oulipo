package com.common.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CopyDto extends ResourceSupport implements Identifiable<Link> {
    private Integer idField;

    private Integer projectId;

    private String value;

    private List<CopyEditDto> edits;
}
