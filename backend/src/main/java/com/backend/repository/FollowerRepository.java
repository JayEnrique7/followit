package com.backend.repository;

import com.backend.dto.Follower;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowerRepository extends CrudRepository<Follower, Integer> {
    List<Follower> findFollowerByUsersId(Integer id);
    List<Follower> findFollowerByFollowerId(Integer id);
    Optional<Follower> findByFollowerId(Integer id);
    @Query(value = "SELECT * FROM follower WHERE follower_id = :id", nativeQuery = true)
    List<Follower> getFollowers(@Param("id") Integer id);
}
