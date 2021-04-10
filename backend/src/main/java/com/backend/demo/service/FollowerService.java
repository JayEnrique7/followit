package com.backend.demo.service;

import com.backend.demo.dto.Follower;
import com.backend.demo.dto.Users;
import com.backend.demo.exceptions.NotFoundException;
import com.backend.demo.model.FollowListResponse;
import com.backend.demo.model.FollowersListResponse;
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
        List<Users> follower = new ArrayList<>();
        List<Users> following = new ArrayList<>();

        for (Follower f : followerList) {
            follower.add(getUser(f.getFollowerId()));
        }

        for (Follower f : followingList) {
            following.add(getUser(f.getFollowerId()));
        }

        return new FollowListResponse(follower, follower.size(), following, following.size());
    }

    private Users getUser(Integer id) {
        Users users = usersService.findUserById(id);
        users.setCredentials(null);
        users.setInfo(null);
        return usersService.findUserById(id);
    }

}
