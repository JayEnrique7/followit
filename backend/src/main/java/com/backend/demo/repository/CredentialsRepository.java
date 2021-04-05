package com.backend.demo.repository;

import com.backend.demo.dto.Credentials;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CredentialsRepository extends CrudRepository<Credentials, Integer> {
    Optional<Credentials> findCredentialsById(Integer id);
}