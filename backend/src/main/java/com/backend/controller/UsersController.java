package com.backend.controller;

import com.backend.constant.PathConstant;
import com.backend.controller.api.BaseControllerApi;
import com.backend.controller.views.UsersView;
import com.backend.dto.Users;
import com.backend.model.ProfileResponse;
import com.backend.model.UsersProfileEditRequest;
import com.backend.service.UsersService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
public class UsersController extends BaseControllerApi<UsersService> {

    public UsersController(UsersService usersService){
        super(usersService);
    }

    @GetMapping(value = PathConstant.URL_GET_PROFILE, produces = {"application/json"})
    public ProfileResponse getProfile(@PathVariable @NotNull Integer id) {
        return getService().profileResponse(id);
    }

    @CrossOrigin
    @JsonView(UsersView.Info.class)
    @PutMapping(path = PathConstant.URL_EDIT_PROFILE, produces = {"application/json"})
    public Users editProfile(@RequestBody @NotNull UsersProfileEditRequest usersProfileEditRequest) {
        return getService().editProfile(usersProfileEditRequest);
    }
}
