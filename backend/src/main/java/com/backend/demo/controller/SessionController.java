package com.backend.demo.controller;

import com.backend.demo.controller.api.BaseControllerApi;
import com.backend.demo.model.SessionRequest;
import com.backend.demo.service.SessionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import static com.backend.demo.constant.PathConstant.URL_LOGIN;
import static com.backend.demo.constant.PathConstant.URL_LOGOUT;

@RestController
public class SessionController extends BaseControllerApi<SessionService> {


    public SessionController(SessionService service) {
        super(service);
    }

    @CrossOrigin
    @PostMapping(value = URL_LOGIN, produces = {"application/json"})
    public ResponseEntity<String> login(@RequestBody @NotNull SessionRequest sessionRequest) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", getService().findCredentialId(sessionRequest));
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body("");
    }

    @CrossOrigin
    @PostMapping(value = URL_LOGOUT, produces = {"application/json"})
    public void logout(HttpServletRequest request) {
        getService().logout(request.getHeader("Authorization"));
    }
}
