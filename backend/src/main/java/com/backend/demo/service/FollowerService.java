package com.backend.demo.service;

import com.backend.demo.repository.FollowerRepository;

import org.springframework.stereotype.Service;

@Service
public class FollowerService {

    public final FollowerRepository followerRepository;

    public FollowerService(FollowerRepository followerRepository) {
        this.followerRepository = followerRepository;
    }
}
