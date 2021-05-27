package com.backend.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class MessagesAllResponse {
    @Setter
    @Getter
    String userName;
    @Setter
    @Getter
    String message;

    public MessagesAllResponse(String userName, String message) {
        this.userName = userName;
        this.message = message;
    }
}
