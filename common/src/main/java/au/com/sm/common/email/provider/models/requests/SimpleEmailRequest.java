package au.com.sm.common.email.provider.models.requests;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class SimpleEmailRequest {

    private Set<String> recipients = new LinkedHashSet<>();
    private Set<String> copiedRecipients = new LinkedHashSet<>();
    private Set<String> blindCopyRecipients = new LinkedHashSet<>();

    private String subject;
    private String message;


    public SimpleEmailRequest(final String message, final String... recipients) {
        this.recipients.addAll(Arrays.stream(recipients).collect(Collectors.toSet()));
        this.message = message;
    }

    public SimpleEmailRequest(final String message, final Set<String> recipients) {
        this.recipients = recipients;
        this.message = message;
    }

    public Set<String> getRecipients() {
        return recipients;
    }

    public Set<String> getCopiedRecipients() {
        return copiedRecipients;
    }

    public Set<String> getBlindCopyRecipients() {
        return blindCopyRecipients;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void addCopiedRecipients(final Set<String> copiedRecipients) {
        if (!copiedRecipients.isEmpty()) {
            this.copiedRecipients.addAll(copiedRecipients);
        }
    }

    public void addCopiedRecipients(final String copiedRecipient) {
        this.copiedRecipients.add(copiedRecipient);
    }

    public void addBlindCopRecipients(final Set<String> blindCopyRecipients) {
        if (!blindCopyRecipients.isEmpty()) {
            this.blindCopyRecipients.addAll(blindCopyRecipients);
        }
    }

    public void addBlindCopyRecipients(final String blindCopyRecipient) {
        this.blindCopyRecipients.add(blindCopyRecipient);
    }
}
