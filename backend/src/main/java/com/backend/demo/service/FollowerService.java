package com.backend.demo.service;

import com.backend.demo.repository.FollowerRepository;

public class FollowerService {

    public final FollowerRepository followerRepository;

    public FollowerService(FollowerRepository followerRepository) {
        this.followerRepository = followerRepository;
    }
}
