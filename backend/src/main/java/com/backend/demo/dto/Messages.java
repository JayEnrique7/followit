package com.backend.demo.dto;

import javax.persistence.*;

@Entity
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    private Users usersId;
    @Column(name = "message")
    private String message;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_message_id", referencedColumnName = "id")
    private Users userMessageId;

    public Integer getId() {
        return id;
    }

    public Users getUsersId() {
        return usersId;
    }

    public void setUsersId(Users usersId) {
        this.usersId = usersId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Users getUserMessageId() {
        return userMessageId;
    }

    public void setUserMessageId(Users userMessageId) {
        this.userMessageId = userMessageId;
    }
}
