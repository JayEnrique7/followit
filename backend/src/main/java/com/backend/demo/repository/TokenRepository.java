package com.backend.demo.repository;

import com.backend.demo.dto.Token;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TokenRepository extends CrudRepository<Token, String> {
    Optional<Token> findTokenByUserId(int id);
    Iterable<Token> findTokenByTokenId(String uuid);
}
