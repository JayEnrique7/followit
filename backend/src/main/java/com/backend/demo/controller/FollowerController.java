package com.backend.demo.controller;

import com.backend.demo.controller.api.BaseControllerApi;
import com.backend.demo.model.FollowListResponse;
import com.backend.demo.model.ProfileResponse;
import com.backend.demo.service.FollowerService;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

import static com.backend.demo.constant.PathConstant.*;

@RestController
public class FollowerController extends BaseControllerApi<FollowerService> {

    public FollowerController(FollowerService service) {
        super(service);
    }

    @CrossOrigin
    @GetMapping(value = URL_FOLLOW_LIST, produces = {"application/json"})
    public FollowListResponse getAllFollower(@PathVariable @NotNull Integer id) {
        return getService().response(id);
    }

    @CrossOrigin
    @PutMapping(value = URL_FOLLOW_USER, produces = {"application/json"})
    public ProfileResponse followUser(@PathVariable @NotNull Integer id) {
        return getService().follow(id);
    }

    @CrossOrigin
    @PutMapping(value = URL_UNFOLLOW_USER, produces = {"application/json"})
    public ProfileResponse unfollowUser(@PathVariable @NotNull Integer id) {
        return getService().unfollow(id);
    }
}
