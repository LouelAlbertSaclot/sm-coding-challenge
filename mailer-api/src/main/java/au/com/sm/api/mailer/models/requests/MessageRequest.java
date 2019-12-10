package au.com.sm.api.mailer.models.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageRequest {

    @JsonProperty(value = "message", required = true)
    @NotEmpty(message = "{message.content.empty}")
    private String message;
    @JsonProperty(value = "subject")
    private String subject;
    @JsonProperty(value = "recipients", required = true)
    private Set<@Email(message = "{message.email.invalid}")
        @NotBlank(message = "{message.email.blank}") String> recipients;
    @JsonProperty(value = "copied")
    private Set<@Email(message = "{message.email.invalid}")
        @NotBlank(message = "{message.email.blank}") String> copied = new HashSet<>();
    @JsonProperty(value = "blindCopied")
    private Set<@Email(message = "{message.email.invalid}")
        @NotBlank(message = "{message.email.blank}") String> blindCopied = new HashSet<>();;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Set<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(Set<String> recipients) {
        this.recipients = recipients;
    }

    public Set<String> getCopied() {
        return copied;
    }

    public void setCopied(Set<String> copied) {
        this.copied = copied;
    }

    public Set<String> getBlindCopied() {
        return blindCopied;
    }

    public void setBlindCopied(Set<String> blindCopied) {
        this.blindCopied = blindCopied;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
