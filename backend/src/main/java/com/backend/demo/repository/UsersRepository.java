package com.backend.demo.repository;

import com.backend.demo.dto.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<Users, String> {
    Iterable<Users> findUserByEmail(String email);
    Optional<Users> findById(long id);
}
