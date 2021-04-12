package com.backend.repository;

import com.backend.dto.Credentials;
import org.springframework.data.repository.CrudRepository;

public interface CredentialsRepository extends CrudRepository<Credentials, Integer> {}