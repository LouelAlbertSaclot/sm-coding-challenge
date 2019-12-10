package au.com.sm.client.sendgrid.models;

import au.com.sm.client.sendgrid.models.details.Content;
import au.com.sm.client.sendgrid.models.details.EmailData;
import au.com.sm.client.sendgrid.models.details.Personalization;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.LinkedHashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SendGridSendRequest {

    @JsonProperty(value = "personalizations", required = true)
    private Set<Personalization> personalizations = new LinkedHashSet<>();
    @JsonProperty(value = "content", required = true)
    private Set<Content>  contents = new LinkedHashSet<>();
    @JsonProperty(value = "from", required = true)
    private EmailData from;
    @JsonProperty(value = "subject", required = true)
    private String subject;

    public Set<Personalization> getPersonalizations() {
        return personalizations;
    }

    public void setPersonalizations(Set<Personalization> personalizations) {
        this.personalizations = personalizations;
    }

    public EmailData getFrom() {
        return from;
    }

    public void setFrom(EmailData from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Set<Content> getContents() {
        return contents;
    }

    public void setContents(Set<Content> contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
