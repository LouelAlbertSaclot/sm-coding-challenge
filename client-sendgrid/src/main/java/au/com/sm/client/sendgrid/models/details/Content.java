package au.com.sm.client.sendgrid.models.details;

import au.com.sm.client.sendgrid.models.enums.ContentType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Content {

    @JsonProperty(value = "type", required = true)
    private ContentType type;
    @JsonProperty(value = "value", required = true)
    private String value;

    public Content() {
    }

    public Content(ContentType type, String value) {
        this.type = type;
        this.value = value;
    }

    public ContentType getType() {
        return type;
    }

    public void setType(ContentType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
