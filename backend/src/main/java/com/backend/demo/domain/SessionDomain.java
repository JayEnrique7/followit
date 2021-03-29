package com.backend.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
public class SessionDomain {
    @NonNull
    @Getter
    private String email;
    @NonNull
    @Getter
    private String secret;
}