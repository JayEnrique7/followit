package com.backend.demo.controller;

import com.backend.demo.controller.api.BaseControllerApi;
import com.backend.demo.dto.Messages;
import com.backend.demo.service.MessagesService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class MessagesController extends BaseControllerApi<MessagesService> {

    public MessagesController(MessagesService service) {
        super(service);
    }

    @CrossOrigin
    @GetMapping(path = "/api/messages/{id}", produces = {"application/json"})
    public Optional<Messages> message(@PathVariable Integer id) {
        return getService().messages(id);
    }

}
