package com.devteria.gateway.services;

import com.devteria.gateway.dto.request.ApiResponse;
import com.devteria.gateway.dto.request.IntrospectRequest;
import com.devteria.gateway.dto.response.IntrospectResponse;
import com.devteria.gateway.proxies.IdentityProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class IdentityService {
    private final IdentityProxy proxy;

    public Mono<ApiResponse<IntrospectResponse>> introspect(String token) {
        return proxy.introspect(IntrospectRequest.builder().token(token).build());
    }
}
