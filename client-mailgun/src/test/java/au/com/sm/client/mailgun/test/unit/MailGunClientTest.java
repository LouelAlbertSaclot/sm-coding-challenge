package au.com.sm.client.mailgun.test.unit;

import au.com.sm.client.mailgun.MailGunClient;
import au.com.sm.client.mailgun.MailGunClientImpl;
import au.com.sm.client.mailgun.config.MailGunProperties;
import au.com.sm.common.email.provider.models.requests.SimpleEmailRequest;
import au.com.sm.common.email.provider.models.responses.SimpleEmailResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest({MailGunClientImpl.class})
@AutoConfigureWebClient
public class MailGunClientTest {

    @Mock
    private RestTemplate restTemplate;

    private MailGunClient client;

    @Before
    public void setup() {
        this.client = new MailGunClientImpl(restTemplate, new MailGunProperties.Builder(
                UriComponentsBuilder.newInstance().build().toUri(), new HttpHeaders())
                    .addApiKey("api-key").addSender("sender@email.com").build());
    }

    @Test
    public void test_sendSimpleEmail_success() throws IOException {

        InputStream stream = this.getClass().getResourceAsStream("/mailgun-email-success-200.json");
        ResponseEntity<SimpleEmailResponse> responseEntity = new ResponseEntity<>(
                new ObjectMapper().readValue(stream, SimpleEmailResponse.class), HttpStatus.OK);

        when(restTemplate.postForEntity(any(), any(), eq(SimpleEmailResponse.class))).thenReturn(responseEntity);

        ResponseEntity<SimpleEmailResponse> response = client.sendPlainTextEmail(new SimpleEmailRequest("message", new HashSet<>()));
         assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
         assertThat(response.getBody()).isInstanceOf(SimpleEmailResponse.class);
    }
}
