package com.backend.demo.dto;

import com.backend.demo.controller.views.UsersView;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;

@Entity
@Table(name = "info")
public class Info {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @JsonView(UsersView.Info.class)
    @Column(name = "bio")
    private String bio;

    public Integer getId() {
        return id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
