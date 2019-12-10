package au.com.sm.services.mailer;

import au.com.sm.services.mailer.exceptions.ClientErrorException;

import java.util.Set;

public interface MailerService {

    String sendSimpleEmail(final String message, final String subject, final Set<String>... recipients) throws ClientErrorException;
}
