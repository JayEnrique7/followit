package com.backend.demo.repository;

import com.backend.demo.dto.Follower;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FollowerRepository extends CrudRepository<Follower, Integer> {
    Optional<Follower> findFollowerById(Integer id);
}
