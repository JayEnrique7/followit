package com.backend.demo.controller;

import com.backend.demo.controller.api.BaseControllerApi;
import com.backend.demo.model.FollowersListResponse;
import com.backend.demo.service.FollowerService;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

import static com.backend.demo.constant.PathConstant.URL_FOLLOW_LIST;

@RestController
public class FollowerController extends BaseControllerApi<FollowerService> {

    public FollowerController(FollowerService service) {
        super(service);
    }

    @CrossOrigin
    @GetMapping(value = URL_FOLLOW_LIST, produces = {"application/json"})
    public FollowersListResponse getAllFollower(@PathVariable @NotNull Integer id) {
        return getService().response(id);
    }
}
