package com.kirilarsov.mtls.client.service.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kirilarsov.mtls.client.config.ClientConfig;
import com.kirilarsov.mtls.client.util.CheckedExceptionHandler;
import com.kirilarsov.mtls.client.exception.BadRequestException;
import com.kirilarsov.mtls.client.exception.BaseException;
import com.kirilarsov.mtls.client.exception.InternalServerException;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.SSLContexts;
import org.springframework.http.HttpStatus;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Properties;


public abstract class BaseHttpClient {
    protected final HttpClient client;
    protected final String baseUrl;
    protected final ObjectMapper objectMapper;

    protected static class HttpUtil {
        public static final String ACCEPT_HEADER_NAME = "Accept";
        public static final String ACCEPT_HEADER_VALUE = "application/json";
        public static final String CONTENT_TYPE_HEADER_NAME = "Content-type";
        public static final String CONTENT_TYPE_HEADER_VALUE = "application/json";

        private HttpUtil() {
        }
    }

    protected BaseHttpClient(String baseUrl, boolean sslEnabled, ClientConfig.Integrations.SslInfo sslInfo) {
        SSLContext sslcontext = null;
        this.baseUrl = baseUrl;
        this.objectMapper =
                JsonMapper.builder()
                        .addModule(new JavaTimeModule())
                        .build()
                        .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        if (sslEnabled) {
            if (sslInfo.getSelfSignedEnabled()) {
                final Properties props = System.getProperties();
                props.setProperty("jdk.internal.httpclient.disableHostnameVerification", Boolean.TRUE.toString());
            }
            try {
                KeyStore trustStore = KeyStore.getInstance(sslInfo.getKeyStoreType());

                FileInputStream instream = new FileInputStream(new File(sslInfo.getKeyStore()));
                try {
                    trustStore.load(instream, sslInfo.getKeyStorePassword().toCharArray());
                } finally {
                    instream.close();
                }

                sslcontext = SSLContexts.custom()
                        .loadKeyMaterial(trustStore, sslInfo.getKeyStorePassword().toCharArray())
                        .loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
                        .setProtocol("TLS")
                        .build();
            } catch (KeyStoreException | NoSuchAlgorithmException | IOException | KeyManagementException |
                     CertificateException | UnrecoverableKeyException e) {
                throw new InternalServerException(e.hashCode(), e.getMessage());
            }
            this.client = HttpClient.newBuilder().sslContext(sslcontext).build();
        } else {
            this.client = HttpClient.newBuilder().build();
        }
    }

    protected <T> T handleResponse(HttpResponse<String> response, Class<T> responseType) {

        if (response.statusCode() >= HttpStatus.OK.value()
                && response.statusCode() < HttpStatus.MULTIPLE_CHOICES.value()) {
            if (responseType.equals(Void.class)) {
                return null;
            } else {
                return CheckedExceptionHandler.handleCheckedException(objectMapper::readValue, response.body(), responseType);
            }
        } else if (response.statusCode() >= HttpStatus.BAD_REQUEST.value()
                && response.statusCode() < HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            throw new BadRequestException(response.statusCode(), response.body());
        } else if (response.statusCode() >= HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            throw new InternalServerException(response.statusCode(), response.body());
        } else throw new BaseException(response.statusCode(), response.body());
    }
}
