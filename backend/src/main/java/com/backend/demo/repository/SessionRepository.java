package com.backend.demo.repository;

import com.backend.demo.dto.Session;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SessionRepository extends CrudRepository<Session, String> {
    Optional<Session> findSessionByUserId(int id);
    Iterable<Session> findSessionByUuid(String uuid);
    Optional<Session> findSessionByToken(String token);
}
