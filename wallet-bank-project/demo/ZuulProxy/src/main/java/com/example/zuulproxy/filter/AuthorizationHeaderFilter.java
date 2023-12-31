package com.example.zuulproxy.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationHeaderFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String authorizationHeader = ctx.getRequest().getHeader("Authorization");
        if (authorizationHeader != null) {
            ctx.addZuulRequestHeader("Authorization", authorizationHeader);
        }
        return null;
    }
}