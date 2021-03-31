package com.backend.demo.repository;

import com.backend.demo.dto.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
    Iterable<User> findUserByEmail(String email);
}
