package models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiResponse {

    @JsonProperty("result")
    private String result;

    @JsonProperty("message")
    private String message;

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }
}