package au.com.sm.client.mailgun;

import au.com.sm.client.mailgun.config.MailGunProperties;
import au.com.sm.common.email.provider.models.requests.SimpleEmailRequest;
import au.com.sm.common.email.provider.models.responses.SimpleEmailResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class MailGunClientImpl implements MailGunClient {

    private static final Logger LOG = LoggerFactory.getLogger(MailGunClientImpl.class);

    private static final String BASIC_AUTH_USER = "api";
    private static final String FORM_KEY_FROM = "from";
    private static final String FORM_KEY_TO = "to";
    private static final String FORM_KEY_CC = "cc";
    private static final String FORM_KEY_BCC = "bcc";
    private static final String FORM_KEY_SUBJECT = "subject";
    private static final String FORM_KEY_MESSAGE = "text";

    private RestTemplate mailGunRestTemplate;
    private MailGunProperties properties;

    public MailGunClientImpl(final RestTemplate mailGunRestTemplate, final MailGunProperties properties) {
        this.mailGunRestTemplate = mailGunRestTemplate;
        this.properties = properties;
    }

    @Override
    public ResponseEntity<SimpleEmailResponse> sendPlainTextEmail(final SimpleEmailRequest content) {

        LOG.debug(String.format("Sending this email as %s", properties.getHeaders().getContentType()));

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add(FORM_KEY_SUBJECT, content.getSubject());
        form.add(FORM_KEY_MESSAGE, content.getMessage());
        form.add(FORM_KEY_FROM, properties.getSender());

        // TODO - there has to be a better way than this
        if (!content.getRecipients().isEmpty()) {
            content.getRecipients().forEach(r -> form.add(FORM_KEY_TO, r));
        }
        if (!content.getCopiedRecipients().isEmpty()) {
            content.getCopiedRecipients().forEach(cc -> form.add(FORM_KEY_CC, cc));
        }
        if (!content.getBlindCopyRecipients().isEmpty()) {
            content.getBlindCopyRecipients().forEach(bcc -> form.add(FORM_KEY_BCC, bcc));
        }

        mailGunRestTemplate.getInterceptors().clear();
        mailGunRestTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(BASIC_AUTH_USER, properties.getApiKey()));

        HttpEntity<MultiValueMap<String, String>> payload = new HttpEntity<>(form, properties.getHeaders());
        return mailGunRestTemplate.postForEntity(properties.getUri(MailGunClient.MESSAGES), payload, SimpleEmailResponse.class);
    }
}
