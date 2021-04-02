package com.backend.demo.service;

import com.backend.demo.repository.CredentialsRepository;
import org.springframework.stereotype.Service;

@Service
public class CredentialsService {

    private final CredentialsRepository credentialsRepository;

    public CredentialsService(CredentialsRepository credentialsRepository) {
        this.credentialsRepository = credentialsRepository;
    }
}
