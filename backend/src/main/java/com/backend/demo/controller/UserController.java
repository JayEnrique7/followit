package com.backend.demo.controller;

import com.backend.demo.controller.api.BaseControllerApi;
import com.backend.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController extends BaseControllerApi<UserService> {

    public UserController(UserService userService){
        super(userService);
    }
}
