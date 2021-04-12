package com.backend.model;

import com.backend.model.utils.UsersDtoJson;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class MessagesPostResponse {
    @Setter
    @Getter
    Integer id;
    @Setter
    @Getter
    UsersDtoJson user;
    @Setter
    @Getter
    String message;
    @Setter
    @Getter
    UsersDtoJson userPosted;

    public MessagesPostResponse(Integer id, UsersDtoJson user, String message, UsersDtoJson userPosted) {
        this.id = id;
        this.user = user;
        this.message = message;
        this.userPosted = userPosted;
    }
}
