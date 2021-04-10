package com.backend.demo.service;

import com.backend.demo.dto.Follower;
import com.backend.demo.dto.Users;
import com.backend.demo.exceptions.NotFoundException;
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

    public FollowersListResponse response(Integer id) {
        List<Follower> followerList = followerRepository.findFollowerByUsersId(id);
        if(followerList.isEmpty()) {
            throw new NotFoundException("Empty Follows");
        }
        List<Users> follow = new ArrayList<>();
        List<Users> follower = new ArrayList<>();

        for (Follower f : followerList) {
            follow.add(getUser(f.getUsersId()));
            follower.add(getUser(f.getFollowerId()));
        }


        return new FollowersListResponse(follow, follow.size(), follower, follower.size());
    }

    private Users getUser(Integer id) {
        return usersService.findUserById(id);
    }

}
