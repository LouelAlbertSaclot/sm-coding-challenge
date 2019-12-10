package au.com.sm.services.mailer;

import au.com.sm.common.email.provider.models.requests.SimpleEmailRequest;
import au.com.sm.common.email.provider.models.responses.SimpleEmailResponse;
import au.com.sm.services.mailer.exceptions.ClientErrorException;
import au.com.sm.services.mailer.manager.MailerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
public class MailerServiceImpl implements MailerService {

    private static final Logger LOG = LoggerFactory.getLogger(MailerServiceImpl.class);

    private MailerManager mailerManager;

    public MailerServiceImpl(final MailerManager mailerManager) {
        this.mailerManager = mailerManager;
    }

    @SafeVarargs
    @Override
    public final String sendSimpleEmail(final String message, final String subject, final Set<String>... recipients) throws ClientErrorException {

        // TODO - Validate inputs (i.e. SQL injections, invalid emails, etc.)
        // Expect to only care about the first 3 varargs [0 - recipients | 1 - copied | 2 - blind copied]
        SimpleEmailRequest request = new SimpleEmailRequest(message, recipients[0]);
        request.addCopiedRecipients(recipients[1]);
        request.addBlindCopRecipients(recipients[2]);
        request.setSubject(subject);

        try {
            ResponseEntity<SimpleEmailResponse> response = mailerManager.sendSimpleEmail(request);
            LOG.info(String.format("Successfully SENT with message - %s", Objects.requireNonNull(response.getBody()).getMessage()));
            return response.getBody().getMessage();
        } catch (final ClientErrorException cex) {
            LOG.error(cex.getMessage(), cex);
            throw cex;
        }
    }
}
