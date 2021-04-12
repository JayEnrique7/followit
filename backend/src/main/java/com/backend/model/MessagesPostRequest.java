package com.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
public class MessagesPostRequest {
    @NonNull
    @Getter
    private String username;
    @NonNull
    @Getter
    private String message;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
