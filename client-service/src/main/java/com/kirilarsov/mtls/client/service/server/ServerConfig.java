package com.kirilarsov.mtls.client.service.server;

import com.kirilarsov.mtls.client.config.ClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {
    @Bean
    ServerHttpClient serverHttpClient(ClientConfig clientConfig) {

            return new ServerHttpClient(
                    clientConfig.getIntegrations().getServerServiceUrl(),
                    clientConfig.getIntegrations().getSslEnabled(),
                    clientConfig.getIntegrations().getSslInfo());

    }
}
