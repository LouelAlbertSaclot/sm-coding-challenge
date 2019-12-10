package au.com.sm.client.mailgun.config;

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
public class MailGunConfig {

    @Value("${provider.mailgun.baseurl}")
    private String baseUrl;

    @Value("${provider.mailgun.apikey}")
    private String apiKey;

    @Value("${provider.mailgun.sender}")
    private String sender;

    @Bean
    public CloseableHttpClient mailGunHttpClient() {
        return HttpClientBuilder.create().build();
    }

    @Bean
    public RestTemplate mailGunRestTemplate(final RestTemplateBuilder builder, final CloseableHttpClient mailGunHttpClient) {
        return builder.requestFactory(() -> new HttpComponentsClientHttpRequestFactory(mailGunHttpClient)).build();
    }

    @Bean
    public MailGunProperties getMailGunProperties() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return new MailGunProperties.Builder(UriComponentsBuilder.fromHttpUrl(this.baseUrl).build().toUri(), headers)
                    .addApiKey(this.apiKey).addSender(this.sender).build();
    }
}
