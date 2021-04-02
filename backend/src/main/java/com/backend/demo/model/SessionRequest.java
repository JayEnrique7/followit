package com.backend.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
public class SessionRequest {
    @NonNull
    @Getter
    private String email;
    @NonNull
    @Getter
    private String secret;
}