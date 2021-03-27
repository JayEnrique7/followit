package com.backend.demo.dto.repository;

import com.backend.demo.dto.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, String> {
}
