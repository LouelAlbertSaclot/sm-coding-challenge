package au.com.sm.client.sendgrid.utils;

import au.com.sm.client.sendgrid.config.SendGridProperties;
import au.com.sm.client.sendgrid.models.SendGridSendRequest;
import au.com.sm.client.sendgrid.models.details.Content;
import au.com.sm.client.sendgrid.models.details.EmailData;
import au.com.sm.client.sendgrid.models.details.Personalization;
import au.com.sm.client.sendgrid.models.enums.ContentType;
import au.com.sm.common.email.provider.models.requests.SimpleEmailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

public class SendGridUtils {

    private static final Logger LOG = LoggerFactory.getLogger(SendGridUtils.class);

    public static SendGridSendRequest mapper(final SimpleEmailRequest source, final ContentType type, final SendGridProperties props) {

        Personalization personalization = new Personalization();
        // TODO - there has to be a better way than this
        if (!source.getRecipients().isEmpty()) {
            personalization.setRecipients(source.getRecipients().stream().map(EmailData::new).collect(Collectors.toSet()));
        }
        if (!source.getCopiedRecipients().isEmpty()) {
            personalization.setCopied(source.getCopiedRecipients().stream().map(EmailData::new).collect(Collectors.toSet()));
        }
        if (!source.getBlindCopyRecipients().isEmpty()) {
            personalization.setBlindCopied(source.getBlindCopyRecipients().stream().map(EmailData::new).collect(Collectors.toSet()));
        }

        SendGridSendRequest destination = new SendGridSendRequest();
        destination.setFrom(new EmailData(props.getSender(), props.getSenderName()));
        destination.getContents().add(new Content(type, source.getMessage()));
        destination.getPersonalizations().add(personalization);
        destination.setSubject(source.getSubject());

        LOG.debug(String.format("Request to send to SendGrid - %s", destination.toString()));
        return destination;
    }
}
