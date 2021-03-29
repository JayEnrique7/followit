package com.backend.demo.dto;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, name = "id")
    private Integer id;
    @Column(unique = true, name = "token")
    private String token;
    @Column(name = "date")
    private Date date;
    @Column(unique = true, name = "user_id")
    private Integer userId;
    @Column(unique = true, name = "token_id")
    private String tokenId;

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

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
}
