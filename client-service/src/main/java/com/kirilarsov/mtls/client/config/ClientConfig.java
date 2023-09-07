package com.kirilarsov.mtls.client.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties(prefix = "client", ignoreUnknownFields = false)
@Validated
public class ClientConfig {
    @NotNull
    private Integrations integrations;

    public Integrations getIntegrations() {
        return integrations;
    }

    public void setIntegrations(Integrations integrations) {
        this.integrations = integrations;
    }

    public static class Integrations {

        @NotNull @NotBlank String serverServiceUrl;
        SslInfo sslInfo;
        @NotNull boolean sslEnabled;

        public SslInfo getSslInfo() {
            return sslInfo;
        }

        public void setSslInfo(SslInfo sslInfo) {
            this.sslInfo = sslInfo;
        }

        public boolean getSslEnabled() {
            return sslEnabled;
        }

        public void setSslEnabled(boolean sslEnabled) {
            this.sslEnabled = sslEnabled;
        }

        public String getServerServiceUrl() {
            return serverServiceUrl;
        }

        public void setServerServiceUrl(String serverServiceUrl) {
            this.serverServiceUrl = serverServiceUrl;
        }

        public static class SslInfo {
            String keyStoreType;
            String keyStorePassword;
            String keyStore;

            String trustStoreType;
            String trustStorePassword;
            String trustStore;
            boolean selfSignedEnabled;

            public String getTrustStoreType() {
                return trustStoreType;
            }

            public void setTrustStoreType(String trustStoreType) {
                this.trustStoreType = trustStoreType;
            }

            public String getTrustStorePassword() {
                return trustStorePassword;
            }

            public void setTrustStorePassword(String trustStorePassword) {
                this.trustStorePassword = trustStorePassword;
            }

            public String getTrustStore() {
                return trustStore;
            }

            public void setTrustStore(String trustStore) {
                this.trustStore = trustStore;
            }

            public String getKeyStoreType() {
                return keyStoreType;
            }

            public void setKeyStoreType(String keyStoreType) {
                this.keyStoreType = keyStoreType;
            }

            public String getKeyStorePassword() {
                return keyStorePassword;
            }

            public void setKeyStorePassword(String keyStorePassword) {
                this.keyStorePassword = keyStorePassword;
            }

            public String getKeyStore() {
                return keyStore;
            }

            public void setKeyStore(String keyStore) {
                this.keyStore = keyStore;
            }

            public boolean getSelfSignedEnabled() {
                return selfSignedEnabled;
            }

            public void setSelfSignedEnabled(boolean selfSignedEnabled) {
                this.selfSignedEnabled = selfSignedEnabled;
            }
        }
    }
}
