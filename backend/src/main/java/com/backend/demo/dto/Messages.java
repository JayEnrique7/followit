package com.backend.demo.dto;

import com.backend.demo.controller.views.MessageView;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;

@Entity
@Table(name = "messages")
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @JsonView(MessageView.Message.class)
    @Column(name = "users_id")
    private Integer usersId;
    @JsonView(MessageView.Message.class)
    @Column(name = "message")
    private String message;
    @JsonView(MessageView.Message.class)
    @Column(name = "user_message_id")
    private Integer userMessageId;

    public Integer getId() {
        return id;
    }

    public Integer getUsersId() {
        return usersId;
    }

    public void setUsersId(Integer usersId) {
        this.usersId = usersId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getUserMessageId() {
        return userMessageId;
    }

    public void setUserMessageId(Integer userMessageId) {
        this.userMessageId = userMessageId;
    }
}
