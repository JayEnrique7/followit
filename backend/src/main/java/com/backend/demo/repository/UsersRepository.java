package com.backend.demo.repository;

import com.backend.demo.dto.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<Users, Integer> {
    Iterable<Users> findUsersByEmail(String email);
    Optional<Users> findUsersById(Integer id);
}
