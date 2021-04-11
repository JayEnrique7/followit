package com.backend.demo.model;

import com.backend.demo.controller.UsersDtoJson;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class FollowListResponse {
    @Setter
    @Getter
    List<UsersDtoJson> userFollower;
    @Setter
    @Getter
    Integer followerSize;
    @Setter
    @Getter
    List<UsersDtoJson> userFollowing;
    @Setter
    @Getter
    Integer followingSize;

    public FollowListResponse(List<UsersDtoJson> userFollower, Integer followerSize, List<UsersDtoJson> userFollowing, Integer followingSize) {
        this.userFollower = userFollower;
        this.followerSize = followerSize;
        this.userFollowing = userFollowing;
        this.followingSize = followingSize;
    }
}
