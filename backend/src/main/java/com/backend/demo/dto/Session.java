package com.backend.demo.dto;

import com.backend.demo.controller.views.SessionView;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "session")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @JsonView(SessionView.Token.class)
    @Column(name = "token")
    private String token;
    @Column(name = "date")
    private Date date;
    @Column(name = "users_id")
    private Integer usersId;
    @Column(name = "uuid")
    private String uuid;
    @JsonView(SessionView.Token.class)
    @Column(name = "email")
    private String email;

    public Integer getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getUsersId() {
        return usersId;
    }

    public void setUsersId(Integer users_id) {
        this.usersId = users_id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
