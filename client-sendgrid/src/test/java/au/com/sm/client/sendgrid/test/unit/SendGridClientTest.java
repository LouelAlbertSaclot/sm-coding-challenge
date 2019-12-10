package au.com.sm.client.sendgrid.test.unit;

import au.com.sm.client.sendgrid.SendGridClient;
import au.com.sm.client.sendgrid.SendGridClientImpl;
import au.com.sm.client.sendgrid.config.SendGridProperties;
import au.com.sm.client.sendgrid.models.SendGridSendRequest;
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
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest({SendGridClientImpl.class})
@AutoConfigureWebClient
public class SendGridClientTest {

    @Mock
    private RestTemplate restTemplate;

    private SendGridClient client;

    @Before
    public void setup() {
        restTemplate = mock(RestTemplate.class);
        this.client = new SendGridClientImpl(restTemplate, new SendGridProperties.Builder(
                UriComponentsBuilder.newInstance().build().toUri(), new HttpHeaders())
                .addApiKey("api-key").addSender("sender@email.com").build());
    }

    @Test
    public void test_sendSimpleEmail_success() throws IOException {

        when(restTemplate.postForEntity(any(), any(), eq(Void.class))).thenReturn(new ResponseEntity<>(HttpStatus.ACCEPTED));

        ResponseEntity<SimpleEmailResponse> response = client.sendPlainTextEmail(new SimpleEmailRequest("message", new HashSet<>()));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isInstanceOf(SimpleEmailResponse.class);
    }
}
