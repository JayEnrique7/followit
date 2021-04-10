package com.backend.demo.repository;

import com.backend.demo.dto.Follower;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FollowerRepository extends CrudRepository<Follower, Integer> {
    List<Follower> findFollowerByUsersId(Integer id);
    List<Follower> findFollowerByFollowerId(Integer id);
}
