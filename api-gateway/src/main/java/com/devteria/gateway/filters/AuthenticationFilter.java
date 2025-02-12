package com.devteria.gateway.filters;

import com.devteria.gateway.services.IdentityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter implements GlobalFilter, Ordered {
    private final IdentityService identityService;
    private final List<String> publicEndpoints = List.of("/identity/auth/.*", "/identity/users/registration");
    @Value("${app.api-prefix}")
    private String apiPrefix;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        List<String> authHeaders = exchange.getRequest().getHeaders().get((HttpHeaders.AUTHORIZATION));
        if (isPublicEndpoint(exchange.getRequest())) {
            return chain.filter(exchange);
        }

        if (CollectionUtils.isEmpty(authHeaders)) {
            return unauthenticated(exchange.getResponse());
        }

        String token = authHeaders.getFirst().replace("Bearer ", "");
        log.info("Validating token : {}", token);
        return identityService.introspect(token).flatMap(response -> {
            if (response.getResult().isValid()) {
                return chain.filter(exchange);
            } else {
                return unauthenticated(exchange.getResponse());
            }
        }).onErrorResume(throwable -> unauthenticated(exchange.getResponse()));
    }

    @Override
    public int getOrder() {
        return -1; //ensure this is the first filter.
    }

    private Mono<Void> unauthenticated(ServerHttpResponse response) {
        String message = "Unauthenticated";
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(message.getBytes())));
    }

    private boolean isPublicEndpoint(ServerHttpRequest request) {
        return publicEndpoints.stream()
                .map(endpoint -> apiPrefix + endpoint)
                .toList()
                .stream()
                .anyMatch(endpoint -> request.getURI().getPath().matches(endpoint)); // use matches because it can handle regex
    }
}
