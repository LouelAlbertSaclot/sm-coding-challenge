package au.com.sm.common.email.provider;

import au.com.sm.common.email.provider.models.requests.SimpleEmailRequest;
import au.com.sm.common.email.provider.models.responses.SimpleEmailResponse;
import org.springframework.http.ResponseEntity;

public interface Client {

    ResponseEntity<SimpleEmailResponse> sendPlainTextEmail(final SimpleEmailRequest request);

    ResponseEntity<SimpleEmailResponse> sendHTMLEmail();
}
