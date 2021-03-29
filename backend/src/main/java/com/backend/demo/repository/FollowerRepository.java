package com.backend.demo.repository;

import com.backend.demo.dto.Follower;
import org.springframework.data.repository.CrudRepository;

public interface FollowerRepository extends CrudRepository<Follower, Integer> {
}
