package com.backend.demo.controller;

import com.backend.demo.dto.Follower;
import com.backend.demo.repository.FollowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class FollowerController {

    @Autowired
    private FollowerRepository followerRepository;

    @GetMapping(path = "followers/all")
    public @ResponseBody Iterable<Follower> getAllFollower() {
        return followerRepository.findAll();
    }
}
