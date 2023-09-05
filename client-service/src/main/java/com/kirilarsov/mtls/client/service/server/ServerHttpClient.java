package com.kirilarsov.mtls.client.service.server;

import com.kirilarsov.mtls.client.config.ClientConfig;
import com.kirilarsov.mtls.client.util.CheckedExceptionHandler;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class ServerHttpClient extends BaseHttpClient {

    public ServerHttpClient(String baseUrl, boolean sslEnabled, ClientConfig.Integrations.SslInfo sslInfo) {
        super(baseUrl,sslEnabled, sslInfo);
    }

    public boolean health() {
        HttpRequest request =
                HttpRequest.newBuilder(URI.create(baseUrl.concat("/health"))).GET().build();
        HttpResponse<String> response =
                CheckedExceptionHandler.handleCheckedException(client::send, request, HttpResponse.BodyHandlers.ofString());

        return handleResponse(response, Boolean.class);
    }
}
