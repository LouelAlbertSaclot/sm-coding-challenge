package au.com.sm.api.mailer.controllers;

import au.com.sm.api.mailer.models.responses.MessageResponse;
import au.com.sm.api.mailer.models.requests.MessageRequest;
import au.com.sm.services.mailer.MailerService;
import au.com.sm.services.mailer.exceptions.ClientErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/mailer")
public class MailerController {

    private static final Logger LOG = LoggerFactory.getLogger(MailerController.class);

    private MailerService mailerService;

    public MailerController(final MailerService mailerService) {
        this.mailerService = mailerService;
    }

    @PostMapping(path = "/simple")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MessageResponse> sendSimpleMessage(@Valid @RequestBody final MessageRequest request) {

        try {
            LOG.debug(String.format("Email Request Received - %s", request.toString()));
            String message = mailerService.sendSimpleEmail(request.getMessage(), request.getSubject(),
                                request.getRecipients(), request.getCopied(), request.getBlindCopied());

            LOG.debug(String.format("Email Response Message - %s", request.toString()));
            return ResponseEntity.ok(new MessageResponse(message));
        } catch (final ClientErrorException cex) {
            LOG.error(cex.getMessage(), cex);
            return ResponseEntity.badRequest().body(new MessageResponse(cex.getMessage(), cex.getErrors()));
        } catch (final Exception ex) {
            LOG.error("Unexpected Exception", ex);
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
