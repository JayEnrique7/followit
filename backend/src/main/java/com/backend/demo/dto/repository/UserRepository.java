package com.backend.demo.dto.repository;

import com.backend.demo.dto.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

}
