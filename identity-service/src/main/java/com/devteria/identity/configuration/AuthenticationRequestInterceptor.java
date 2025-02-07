package com.devteria.identity.configuration;

import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Slf4j
public class AuthenticationRequestInterceptor implements feign.RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String authHeader = "Authorization";
        String token = servletRequestAttributes.getRequest().getHeader(authHeader);
        if (StringUtils.hasText(token)) {
            log.info("Token: {}", token);
            requestTemplate.header(authHeader, List.of(token));
        }
    }
}
