package com.backend.demo.service;

import com.backend.demo.controller.UsersDtoJson;
import com.backend.demo.dto.Follower;
import com.backend.demo.exceptions.ConflictAlreadyExistsException;
import com.backend.demo.exceptions.NotFoundException;
import com.backend.demo.exceptions.UnacceptableException;
import com.backend.demo.model.FollowListResponse;
import com.backend.demo.model.ProfileResponse;
import com.backend.demo.repository.FollowerRepository;

import com.backend.demo.utils.SessionUtil;
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

    public ProfileResponse follow(Integer id) {
        followingController(id);
        Integer currentUserId = SessionUtil.getCurrentUser().getUsersId();
        followerRepository.findByFollowerId(currentUserId)
                .stream()
                .filter(f -> f.getUsersId().equals(id))
                .findAny()
                .ifPresent(found -> {
                    throw new ConflictAlreadyExistsException("You already follow this user!");
                });
        Follower follower = new Follower();
        follower.setUsersId(id);
        follower.setFollowerId(currentUserId);
        followerRepository.save(follower);
        return new ProfileResponse(usersService.usersDtoJsonById(id), usersService.userIsFollower(id));
    }

    public ProfileResponse unfollow(Integer id) {
        followingController(id);
        Follower follower = followerRepository.findByFollowerId(SessionUtil.getCurrentUser().getUsersId())
                .stream()
                .filter(f -> f.getUsersId().equals(id))
                .findFirst()
                .orElseThrow(() -> { throw new NotFoundException("The current user doesn't follow"); });
        followerRepository.delete(follower);
        return new ProfileResponse(usersService.usersDtoJsonById(id), false);
    }

    private void followingController(Integer id) {
        if (id.equals(SessionUtil.getCurrentUser().getUsersId())) {
            throw new UnacceptableException("Restriction to follow oneself!");
        }
    }

    public Optional<Follower> followerOptional(Integer id) {
        return followerRepository.findByFollowerId(id);
    }
}
