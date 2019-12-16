package au.com.sm.client.sendgrid;

import au.com.sm.client.sendgrid.config.SendGridProperties;
import au.com.sm.client.sendgrid.models.SendGridSendRequest;
import au.com.sm.client.sendgrid.models.enums.ContentType;
import au.com.sm.client.sendgrid.utils.SendGridUtils;
import au.com.sm.common.email.provider.models.requests.SimpleEmailRequest;
import au.com.sm.common.email.provider.models.responses.SimpleEmailResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class SendGridClientImpl implements SendGridClient {

    private static final Logger LOG = LoggerFactory.getLogger(SendGridClientImpl.class);

    private RestTemplate sendGridRestTemplate;
    private SendGridProperties properties;

    public SendGridClientImpl(final RestTemplate sendGridRestTemplate, final SendGridProperties properties) {
        this.sendGridRestTemplate = sendGridRestTemplate;
        this.properties = properties;
    }

    @Override
    public ResponseEntity<SimpleEmailResponse> sendPlainTextEmail(final SimpleEmailRequest request) {

        LOG.debug(String.format("Sending this email as %s", properties.getHeaders().getContentType()));
        SendGridSendRequest body = SendGridUtils.mapper(request, ContentType.PLAINTEXT, properties);
        LOG.debug(String.format("Mapped to SendGrid Expected Structure - %s", body.toString()));

        ResponseEntity<Void> responseEntity = sendGridRestTemplate.postForEntity(properties.getUri(SendGridClient.SEND), properties.setPayload(body), Void.class);
        LOG.info(String.format("Request responded with HTTP CODE - %d", responseEntity.getStatusCodeValue()));

        if (responseEntity.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR ||
                responseEntity.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
            throw new HttpClientErrorException(responseEntity.getStatusCode(),
                    String.format("Response received %s", responseEntity.getStatusCode().toString()));
        }

        return ResponseEntity.ok(new SimpleEmailResponse("success"));
    }

    @Override
    public ResponseEntity<SimpleEmailResponse> sendHTMLEmail() {
        throw new UnsupportedOperationException();
    }
}
