package com.common.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CopyEditDto extends ResourceSupport implements Identifiable<Link> {

    private Integer idField;

    private String delta;

    private String authorName;

    private String projectTitle;

    private Integer copyId;

    private Integer projectId;

    private CopyEditStatus status;
}
