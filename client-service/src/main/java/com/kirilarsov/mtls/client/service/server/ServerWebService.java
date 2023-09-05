package com.kirilarsov.mtls.client.service.server;

import org.springframework.stereotype.Service;

@Service
public class ServerWebService {
    private final ServerHttpClient serverHttpClient;

    public ServerWebService(
            ServerHttpClient serverHttpClient) {
        this.serverHttpClient = serverHttpClient;
    }

    public boolean health() {

            return serverHttpClient.health();

    }
}
