// Copyright (c) 2019 Travelex Ltd

package com.gateway.filters.pre;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class RequestLoggingPreFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    public boolean shouldFilter() {
        return true;
    }

    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        log.info("{} request to {}", request.getMethod(), request.getRequestURL().toString());
        if (ctx.getRequestQueryParams() != null) {
            log.info(" |__ with params {}", ctx.getRequestQueryParams());
        }
        return null;
    }

}