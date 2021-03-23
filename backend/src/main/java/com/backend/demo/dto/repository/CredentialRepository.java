package com.backend.demo.dto.repository;

import com.backend.demo.dto.Credential;
import org.springframework.data.repository.CrudRepository;

public interface CredentialRepository extends CrudRepository<Credential, Integer> {
}
