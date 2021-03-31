package com.backend.demo.repository;

import com.backend.demo.dto.Credentials;
import org.springframework.data.repository.CrudRepository;

public interface CredentialsRepository extends CrudRepository<Credentials, String> {
}