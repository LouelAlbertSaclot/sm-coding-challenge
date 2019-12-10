package au.com.sm.api.mailer.test.unit;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import au.com.sm.api.mailer.controllers.MailerController;
import au.com.sm.api.mailer.models.responses.MessageResponse;
import au.com.sm.services.mailer.MailerService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.InputStream;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest({MailerController.class})
public class MailerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MailerService mailerService;

    private ObjectMapper mapper;

    @Before
    public void setUp() {
        this.mapper = new ObjectMapper();
    }

    @Test
    public void test_sendSimpleMessage_success() throws Exception {

        InputStream input = this.getClass().getResourceAsStream("/request-simple-email-valid.json");
        String payload = this.mapper.readValue(input, JsonNode.class).toString();

        InputStream stream = this.getClass().getResourceAsStream("/response-simple-email-200.json");
        ResponseEntity<MessageResponse> responseEntity = new ResponseEntity<>(this.mapper.readValue(stream, MessageResponse.class), HttpStatus.OK);
        when(mailerService.sendSimpleEmail(anyString(), anyString(), any())).thenReturn(Objects.requireNonNull(responseEntity.getBody()).getMessage());

        this.mvc.perform(post("/api/v1/mailer/simple")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(payload))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void test_sendSimpleMessage_failed_invalid_email() throws Exception {

        InputStream input = this.getClass().getResourceAsStream("/request-simple-email-invalid-email.json");
        String payload = this.mapper.readValue(input, JsonNode.class).toString();

        this.mvc.perform(post("/api/v1/mailer/simple")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(payload))
                .andExpect(status().is4xxClientError());
    }
}
