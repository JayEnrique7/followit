package com.backend.demo.controller;

import com.backend.demo.controller.api.BaseControllerApi;
import com.backend.demo.service.UsersService;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsersController extends BaseControllerApi<UsersService> {

    public UsersController(UsersService usersService){
        super(usersService);
    }
}
