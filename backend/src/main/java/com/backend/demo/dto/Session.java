package com.backend.demo.dto;

import com.backend.demo.controller.views.SessionView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "session")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, name = "id")
    private Integer id;
    @JsonView(SessionView.Token.class)
    @Column(unique = true, name = "token")
    private String token;
    @Column(name = "date")
    private Date date;
    @Column(unique = true, name = "user_id")
    private Integer userId;
    @Column(unique = true, name = "uuid")
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
