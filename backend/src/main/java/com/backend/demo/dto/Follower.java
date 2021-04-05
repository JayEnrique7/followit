package com.backend.demo.dto;

import javax.persistence.*;

@Entity
@Table(name = "follower")
public class Follower {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_following_id", referencedColumnName = "id")
    private Users usersFollowing;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_follower_id", referencedColumnName = "id")
    private Users userFollower;

    public Integer getId() {
        return id;
    }

    public Users getUsersFollowing() {
        return usersFollowing;
    }

    public void setUsersFollowing(Users usersFollowing) {
        this.usersFollowing = usersFollowing;
    }

    public Users getUserFollower() {
        return userFollower;
    }

    public void setUserFollower(Users userFollower) {
        this.userFollower = userFollower;
    }
}
