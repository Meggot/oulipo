// Copyright (c) 2019 Travelex Ltd

package com.gateway.security.client;

import com.common.models.dtos.LoginUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "${zuul.routes.user.url}", name = "userClient")
public interface UserFeignClient {

    @PostMapping("/login")
    LoginUser loginWithUserName(@RequestBody String username);

}