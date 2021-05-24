package com.backend.repository;

import com.backend.dto.Session;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SessionRepository extends CrudRepository<Session, String> {
    Optional<Session> findSessionByUsersId(int id);
    Iterable<Session> findSessionByUuid(String uuid);
    Optional<Session> findSessionByToken(String token);
    Long removeByUsersId(int id);
}
