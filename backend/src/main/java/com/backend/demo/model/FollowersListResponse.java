package com.backend.demo.model;

import com.backend.demo.dto.Users;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class FollowersListResponse {
    @Setter
    @Getter
    Users users;
    @Setter
    @Getter
    List<Users> userFollowing;
    @Setter
    @Getter
    Integer getUsersFollowerSize;

    public FollowersListResponse(Users users, List<Users> userFollowing, Integer getUsersFollowerSize) {
        this.users = users;
        this.userFollowing = userFollowing;
        this.getUsersFollowerSize = getUsersFollowerSize;
    }
}
