package com.backend.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
public class UserLogin {
    @NonNull
    @Getter
    private String email;
    @NonNull
    @Getter
    private String secret;
}