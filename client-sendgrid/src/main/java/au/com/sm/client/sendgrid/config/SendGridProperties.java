package au.com.sm.client.sendgrid.config;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.net.URI;

public class SendGridProperties {

    private URI uri;
    private HttpHeaders headers;
    private String apiKey;
    private String sender;
    private String senderName;

    private SendGridProperties(URI uri, HttpHeaders headers, String apiKey, String sender, String senderName) {
        this.uri = uri;
        this.headers = headers;
        this.apiKey = apiKey;
        this.sender = sender;
        this.senderName = senderName;
    }

    public static class Builder {

        private URI uri;
        private HttpHeaders headers;
        private String apiKey;
        private String sender;
        private String senderName;

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

        public Builder addSenderName(final String senderName) {
            this.senderName = senderName;
            return this;
        }

        public SendGridProperties build() {
            return new SendGridProperties(this.uri, this.headers, this.apiKey, this.sender, this.senderName);
        }
    }

    public <T> HttpEntity<T> setPayload(T body) {
        return new HttpEntity<>(body, headers);
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

    public String getSenderName() {
        return senderName;
    }
}
