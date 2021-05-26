package com.backend.controller;

import com.backend.config.utils.JsonBody;
import com.backend.constant.PathConstant;
import com.backend.controller.api.BaseControllerApi;
import com.backend.dto.Users;
import com.backend.model.SessionRequest;
import com.backend.model.SessionResponse;
import com.backend.service.SessionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

@RestController
@CrossOrigin
public class SessionController extends BaseControllerApi<SessionService> {


    public SessionController(SessionService service) {
        super(service);
    }

    public Users users(@AuthenticationPrincipal Users users) {
        return users;
    }

    @PostMapping(value = PathConstant.URL_LOGIN, produces = {"application/json"})
    public SessionResponse login(@RequestBody @NotNull SessionRequest sessionRequest) {
        return getService().loginResponse(sessionRequest);
    }

    @PostMapping(value = PathConstant.URL_LOGOUT, produces = {"application/json"})
    public void logout(HttpServletRequest request) {
        getService().logout(request.getHeader("Authorization"));
    }
}
