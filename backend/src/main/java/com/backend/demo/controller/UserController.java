package com.backend.demo.controller;

import com.backend.demo.controller.api.BaseControllerApi;
import com.backend.demo.controller.domain.UserLogin;
import com.backend.demo.controller.service.UserService;
import com.backend.demo.dto.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

import java.util.List;

@RestController
public class UserController extends BaseControllerApi<UserService> {

    public UserController(UserService userService){
        super(userService);
    }

    @CrossOrigin
    @PostMapping(value = "/login")
    public List<User> login(@RequestBody @NotNull UserLogin userLogin) {
        return getService().findCredentialId(userLogin);
    }
}
