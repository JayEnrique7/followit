package com.backend.repository;

import com.backend.dto.Follower;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FollowerRepository extends CrudRepository<Follower, Integer> {
    List<Follower> findFollowerByUsersId(Integer id);
    List<Follower> findFollowerByFollowerId(Integer id);
    Optional<Follower> findByFollowerId(Integer id);
}