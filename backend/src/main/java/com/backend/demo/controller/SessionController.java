package com.backend.demo.controller;

import com.backend.demo.controller.api.BaseControllerApi;
import com.backend.demo.domain.SessionDomain;
import com.backend.demo.dto.User;
import com.backend.demo.service.SessionService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
public class SessionController extends BaseControllerApi<SessionService> {


    public SessionController(SessionService service) {
        super(service);
    }

    @CrossOrigin
    @PostMapping(value = "/login", produces = {"application/json"})
    public List<User> login(@RequestBody @NotNull SessionDomain sessionDomain) {
        return getService().findCredentialId(sessionDomain);
    }
}
