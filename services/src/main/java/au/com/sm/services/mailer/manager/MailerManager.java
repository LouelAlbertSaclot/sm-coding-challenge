package au.com.sm.services.mailer.manager;

import au.com.sm.common.email.provider.Client;
import au.com.sm.common.email.provider.models.requests.SimpleEmailRequest;
import au.com.sm.common.email.provider.models.responses.SimpleEmailResponse;
import au.com.sm.services.mailer.exceptions.ClientErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class MailerManager {

    private static final Logger LOG = LoggerFactory.getLogger(MailerManager.class);

    private Set<Client> clients = new LinkedHashSet<>();


    public ResponseEntity<SimpleEmailResponse> sendSimpleEmail(final SimpleEmailRequest request) throws ClientErrorException {

        List<String> errors = new ArrayList<>();
        for (Client client : clients) {
            try {
                return client.sendPlainTextEmail(request);
            } catch (final HttpClientErrorException | HttpServerErrorException hex) {
                // Not sure if handling the server errors this way is appropriate
                String msg = String.format("Failed with HTTP status %s - %s", hex.getStatusCode(), hex.getResponseBodyAsString());
                LOG.warn(msg, hex); errors.add(msg);
            }
        }

        // TODO - Provide solution to somehow persist/store request for future retrieval
        LOG.error("Failed to find any email provider to successfully send message ");
        throw new ClientErrorException("Failed to find any email provider to successfully send message", errors);
    }

    public void addProvider(final Client provider) {
        this.clients.add(provider);
    }
}
