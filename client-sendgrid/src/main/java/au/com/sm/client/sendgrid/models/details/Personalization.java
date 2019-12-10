package au.com.sm.client.sendgrid.models.details;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Personalization {

    @JsonProperty(value = "to", required = true)
    private Set<EmailData> recipients;
    @JsonProperty(value = "cc")
    private Set<EmailData> copied;
    @JsonProperty(value = "bcc")
    private Set<EmailData> blindCopied;

    public Set<EmailData> getRecipients() {
        return recipients;
    }

    public void setRecipients(Set<EmailData> recipients) {
        this.recipients = recipients;
    }

    public Set<EmailData> getCopied() {
        return copied;
    }

    public void setCopied(Set<EmailData> copied) {
        this.copied = copied;
    }

    public Set<EmailData> getBlindCopied() {
        return blindCopied;
    }

    public void setBlindCopied(Set<EmailData> blindCopied) {
        this.blindCopied = blindCopied;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
