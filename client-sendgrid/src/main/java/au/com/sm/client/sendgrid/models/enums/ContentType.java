package au.com.sm.client.sendgrid.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum ContentType {

    PLAINTEXT("text/plain"), HTML("text/html");

    private String value;

    ContentType(final String value) {
        this.value = value;
    }

    @JsonCreator
    public static ContentType fromValue(final String value) {
        return Arrays.stream(values()).filter(v -> v.toString().equalsIgnoreCase(value)).findFirst().get();
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }
}
