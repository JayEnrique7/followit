package com.backend.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class TokenDomain {
    @Getter
    private String token;
    @Getter
    private String email;
}
