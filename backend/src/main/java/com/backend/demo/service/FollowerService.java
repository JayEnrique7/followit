package com.backend.demo.service;

import com.backend.demo.controller.UsersDtoJson;
import com.backend.demo.dto.Follower;
import com.backend.demo.dto.Users;
import com.backend.demo.exceptions.NotFoundException;
import com.backend.demo.model.FollowListResponse;
import com.backend.demo.repository.FollowerRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
            follower.add(getUser(f.getFollowerId()));
        }

        for (Follower f : followingList) {
            following.add(getUser(f.getFollowerId()));
        }

        return new FollowListResponse(follower, follower.size(), following, following.size());
    }

    private UsersDtoJson getUser(Integer id) {
        Users users = usersService.findUserById(id);
        return new UsersDtoJson(users.getId(), users.getEmail(), users.getFirstName(), users.getLastName(), users.getInfo());
    }

}
