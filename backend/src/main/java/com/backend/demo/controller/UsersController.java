package com.backend.demo.controller;

import com.backend.demo.controller.api.BaseControllerApi;
import com.backend.demo.controller.views.UsersView;
import com.backend.demo.dto.Users;
import com.backend.demo.model.ProfileResponse;
import com.backend.demo.model.UsersProfileEditRequest;
import com.backend.demo.service.UsersService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

import static com.backend.demo.constant.PathConstant.URL_EDIT_PROFILE;
import static com.backend.demo.constant.PathConstant.URL_GET_PROFILE;

@RestController
public class UsersController extends BaseControllerApi<UsersService> {

    public UsersController(UsersService usersService){
        super(usersService);
    }

    @CrossOrigin
    @GetMapping(value = URL_GET_PROFILE, produces = {"application/json"})
    public ProfileResponse getProfile(@PathVariable @NotNull Integer id) {
        return getService().profileResponse(id);
    }

    @CrossOrigin
    @JsonView(UsersView.Info.class)
    @PutMapping(path = URL_EDIT_PROFILE, produces = {"application/json"})
    public Users editProfile(@RequestBody @NotNull UsersProfileEditRequest usersProfileEditRequest) {
        return getService().editProfile(usersProfileEditRequest);
    }
}
