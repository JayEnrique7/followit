package com.backend.repository;

import com.backend.dto.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<Users, Integer> {
    Optional<Users> findUsersByEmail(String email);
    Optional<Users> findUsersById(Integer id);
}
