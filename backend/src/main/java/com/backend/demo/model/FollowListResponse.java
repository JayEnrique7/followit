package com.backend.demo.model;

import com.backend.demo.dto.Users;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class FollowListResponse {
    @Setter
    @Getter
    List<Users> userFollower;
    @Setter
    @Getter
    Integer followerSize;
    @Setter
    @Getter
    List<Users> userFollowing;
    @Setter
    @Getter
    Integer followingSize;

    public FollowListResponse(List<Users> userFollower, Integer followerSize, List<Users> userFollowing, Integer followingSize) {
        this.userFollower = userFollower;
        this.followerSize = followerSize;
        this.userFollowing = userFollowing;
        this.followingSize = followingSize;
    }
}
