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
    List<Users> usersFollow;
    @Setter
    @Getter
    Integer usersFollowSize;
    @Setter
    @Getter
    List<Users> userFollowing;
    @Setter
    @Getter
    Integer getUsersFollowerSize;

    public FollowersListResponse(List<Users> usersFollow, Integer usersFollowSize, List<Users> userFollowing, Integer getUsersFollowerSize) {
        this.usersFollow = usersFollow;
        this.usersFollowSize = usersFollowSize;
        this.userFollowing = userFollowing;
        this.getUsersFollowerSize = getUsersFollowerSize;
    }
}
