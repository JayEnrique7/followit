package com.backend.model;

import com.backend.model.utils.UsersDtoJson;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class SessionResponse {

    @Setter
    @Getter
    String token;
    @Setter
    @Getter
    UsersDtoJson user;

    public SessionResponse(UsersDtoJson user, String token) {
        this.user = user;
        this.token = token;
    }
}
