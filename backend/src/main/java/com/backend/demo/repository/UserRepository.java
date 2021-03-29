package com.backend.demo.repository;

import com.backend.demo.dto.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, String> {
    Iterable<User> findUserByEmail(String email);
    List<User> findUserById(int id);
}
