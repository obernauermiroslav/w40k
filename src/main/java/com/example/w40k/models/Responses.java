package com.example.w40k.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Responses {

    @JsonProperty("status")
    private String status;

    @JsonProperty("message")
    private String message;

    public Responses(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}

