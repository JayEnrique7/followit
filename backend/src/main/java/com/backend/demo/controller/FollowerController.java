package com.backend.demo.controller;

import com.backend.demo.controller.api.BaseControllerApi;
import com.backend.demo.dto.Follower;
import com.backend.demo.service.FollowerService;
import org.springframework.web.bind.annotation.*;

@RestController
public class FollowerController extends BaseControllerApi<FollowerService> {

    public FollowerController(FollowerService service) {
        super(service);
    }

    @CrossOrigin
    @PostMapping(value = "/api/messages", produces = {"application/json"})
    public @ResponseBody Iterable<Follower> getAllFollower() {
        return null;
    }
}
