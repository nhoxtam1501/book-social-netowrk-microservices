package com.devteria.gateway.configuration;

import com.devteria.gateway.proxies.IdentityProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    @Value("${services.urls.identity_service}")
    private String identityServiceUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl(identityServiceUrl).build();
    }

    @Bean
    public IdentityProxy identityProxy(WebClient webClient) {
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webClient)).build();
        return factory.createClient(IdentityProxy.class);
    }
}
