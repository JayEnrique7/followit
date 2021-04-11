package com.backend.demo.service;

import com.backend.demo.controller.UsersDtoJson;
import com.backend.demo.dto.Follower;
import com.backend.demo.exceptions.NotFoundException;
import com.backend.demo.model.FollowListResponse;
import com.backend.demo.repository.FollowerRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FollowerService {

    public final FollowerRepository followerRepository;
    public final UsersService usersService;

    public FollowerService(FollowerRepository followerRepository, UsersService usersService) {
        this.followerRepository = followerRepository;
        this.usersService = usersService;
    }

    public FollowListResponse response(Integer id) {
        List<Follower> followerList = followerRepository.findFollowerByUsersId(id);
        List<Follower> followingList = followerRepository.findFollowerByFollowerId(id);
        if(followerList.isEmpty() && followingList.isEmpty()) {
            throw new NotFoundException("Empty Follows");
        }
        List<UsersDtoJson> follower = new ArrayList<>();
        List<UsersDtoJson> following = new ArrayList<>();

        for (Follower f : followerList) {
            follower.add(usersService.usersDtoJsonById(f.getFollowerId()));
        }

        for (Follower f : followingList) {
            following.add(usersService.usersDtoJsonById(f.getFollowerId()));
        }

        return new FollowListResponse(follower, follower.size(), following, following.size());
    }

    public Optional<Follower> followerOptional(Integer id) {
        return followerRepository.findByFollowerId(id);
    }
}
