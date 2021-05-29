package com.backend.service;

import com.backend.model.utils.UsersDtoJson;
import com.backend.dto.Follower;
import com.backend.exceptions.ConflictAlreadyExistsException;
import com.backend.exceptions.NotFoundException;
import com.backend.exceptions.UnacceptableException;
import com.backend.model.FollowListResponse;
import com.backend.model.ProfileResponse;
import com.backend.repository.FollowerRepository;

import com.backend.utils.SessionUtil;
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

    public FollowListResponse getAllFollows(Integer id) {
        List<UsersDtoJson> follower = new ArrayList<>();
        List<UsersDtoJson> following = new ArrayList<>();

        followerRepository.findFollowerByUsersId(id).forEach(f -> follower.add(usersService.usersDtoJsonById(f.getFollowerId())));
        followerRepository.findFollowerByFollowerId(id).forEach(f -> following.add(usersService.usersDtoJsonById(f.getUsersId())));

        if(follower.isEmpty() && following.isEmpty()) {
            throw new NotFoundException("Empty Follows");
        }

        return new FollowListResponse(follower, follower.size(), following, following.size());
    }

    public ProfileResponse follow(Integer id) {
        alreadyFollow(SessionUtil.getCurrentUser().getUsersId(), id);
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
        List<Follower> followerList = followerRepository.getFollowers(SessionUtil.getCurrentUser().getUsersId());
        followerList.removeIf(f -> !f.getUsersId().equals(id));
        if(followerList.isEmpty()) {
            throw new NotFoundException("The current user doesn't follow");
        }
        followerList.forEach(followerRepository::delete);
        return new ProfileResponse(usersService.usersDtoJsonById(id), false);
    }

    private void alreadyFollow(Integer userId, Integer follow) {
        followerRepository.findFollowerByUsersId(userId).forEach(f -> {
            if (f.getFollowerId().equals(follow)) {
                throw new UnacceptableException("Already follow");
            }
        });
    }

    public boolean isFollow(Integer userId, Integer follow) {
        return followerRepository.findFollowerByUsersId(userId).stream().anyMatch(f -> f.getFollowerId().equals(follow));
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
