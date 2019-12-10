package au.com.sm.client.mailgun.config;

import org.springframework.http.HttpHeaders;

import java.net.URI;

public class MailGunProperties {

    private URI uri;
    private HttpHeaders headers;
    private String apiKey;
    private String sender;

    private MailGunProperties(final URI uri, final HttpHeaders headers, final String apiKey, final String sender) {
        this.uri = uri;
        this.headers = headers;
        this.apiKey = apiKey;
        this.sender = sender;
    }

    public static class Builder {

        private URI uri;
        private HttpHeaders headers;
        private String apiKey;
        private String sender;

        public Builder(final URI uri, final HttpHeaders headers) {
            this.uri = uri;
            this.headers = headers;
        }

        public Builder addApiKey(final String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public Builder addSender(final String sender) {
            this.sender = sender;
            return this;
        }

        public MailGunProperties build() {
            return new MailGunProperties(this.uri, this.headers, this.apiKey, this.sender);
        }
    }

    public URI getUri() {
        return uri;
    }

    public URI getUri(final String path) {
        return uri.resolve(getUri().toString() + path);
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getSender() {
        return sender;
    }
}
