package com.backend.demo.repository;

import com.backend.demo.dto.Credential;
import org.springframework.data.repository.CrudRepository;

public interface CredentialRepository extends CrudRepository<Credential, String> {
        Iterable<Credential> findCredentialById(int id);
}