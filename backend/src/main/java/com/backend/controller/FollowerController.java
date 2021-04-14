package com.backend.controller;

import com.backend.constant.PathConstant;
import com.backend.controller.api.BaseControllerApi;
import com.backend.model.FollowListResponse;
import com.backend.model.ProfileResponse;
import com.backend.service.FollowerService;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
public class FollowerController extends BaseControllerApi<FollowerService> {

    public FollowerController(FollowerService service) {
        super(service);
    }

    @CrossOrigin
    @GetMapping(value = PathConstant.URL_FOLLOW_LIST, produces = {"application/json"})
    public FollowListResponse getAllFollower(@PathVariable @NotNull Integer id) {
        return getService().getAllFollows(id);
    }

    @CrossOrigin
    @PutMapping(value = PathConstant.URL_FOLLOW_USER, produces = {"application/json"})
    public ProfileResponse followUser(@PathVariable @NotNull Integer id) {
        return getService().follow(id);
    }

    @CrossOrigin
    @PutMapping(value = PathConstant.URL_UNFOLLOW_USER, produces = {"application/json"})
    public ProfileResponse unfollowUser(@PathVariable @NotNull Integer id) {
        return getService().unfollow(id);
    }
}