package com.common.models.dtos;

import lombok.Data;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

@Data
public class AccountLoginDto extends ResourceSupport implements Identifiable<Link> {

    private String inetAddress;

    private String loginTime;

}
