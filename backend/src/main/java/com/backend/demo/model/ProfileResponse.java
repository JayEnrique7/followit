package com.backend.demo.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ProfileResponse {

    @Setter
    @Getter
    UsersDtoJson user;
    @Setter
    @Getter
    Boolean follow;

    public ProfileResponse(UsersDtoJson user, Boolean follow) {
        this.user = user;
        this.follow = follow;
    }
}
