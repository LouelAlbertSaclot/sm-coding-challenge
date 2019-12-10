package au.com.sm.services.mailer.test.unit;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import au.com.sm.client.mailgun.MailGunClient;
import au.com.sm.common.email.provider.models.requests.SimpleEmailRequest;
import au.com.sm.common.email.provider.models.responses.SimpleEmailResponse;
import au.com.sm.services.mailer.MailerService;
import au.com.sm.services.mailer.MailerServiceImpl;
import au.com.sm.services.mailer.exceptions.ClientErrorException;
import au.com.sm.services.mailer.manager.MailerManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest({MailerServiceImpl.class})
public class MailerServicesTest {

    @Autowired
    private MailerService service;

    @MockBean
    private MailerManager manager;

    @Test
    public void test_sendSimpleEmail_success() throws IOException, ClientErrorException {

        InputStream stream = this.getClass().getResourceAsStream("/mailgun-email-success-200.json");
        ResponseEntity<SimpleEmailResponse> responseEntity = new ResponseEntity<>(new ObjectMapper().readValue(stream, SimpleEmailResponse.class), HttpStatus.OK);

        when(manager.sendSimpleEmail(any(SimpleEmailRequest.class))).thenReturn(responseEntity);

        String message = service.sendSimpleEmail("success message", "subject", new HashSet<>(), new HashSet<>(), new HashSet<>());
        assertThat(message).isEqualTo("Queued. Thank you.");
    }

    @Test(expected = ClientErrorException.class)
    public void test_sendSimpleEmail_fail_exception() throws IOException, ClientErrorException {

        when(manager.sendSimpleEmail(any(SimpleEmailRequest.class))).thenThrow(new ClientErrorException("Expected Fail", new ArrayList<>()));
        service.sendSimpleEmail("success message", "subject", new HashSet<>(), new HashSet<>(), new HashSet<>());
    }
}
