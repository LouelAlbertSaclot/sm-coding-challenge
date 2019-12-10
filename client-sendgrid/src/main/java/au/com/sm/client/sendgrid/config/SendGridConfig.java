package au.com.sm.client.sendgrid.config;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
public class SendGridConfig {

    @Value("${provider.sendgrid.baseurl}")
    private String baseUrl;

    @Value("${provider.sendgrid.apikey}")
    private String apiKey;

    @Value("${provider.sendgrid.sender.email}")
    private String sender;

    @Value("${provider.sendgrid.sender.name}")
    private String senderName;

    @Bean
    public CloseableHttpClient sendGridHttpClient() {
        return HttpClientBuilder.create().build();
    }

    @Bean
    public RestTemplate sendGridRestTemplate(final RestTemplateBuilder builder, final CloseableHttpClient sendGridHttpClient) {
        return builder.requestFactory(() -> new HttpComponentsClientHttpRequestFactory(sendGridHttpClient)).build();
    }

    @Bean
    public SendGridProperties getSendGridProperties() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.apiKey);
        return new SendGridProperties.Builder(UriComponentsBuilder.fromHttpUrl(this.baseUrl).build().toUri(), headers)
                .addApiKey(this.apiKey).addSender(this.sender).build();
    }
}
