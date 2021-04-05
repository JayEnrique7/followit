package com.backend.demo.controller;

import com.backend.demo.controller.api.BaseControllerApi;
import com.backend.demo.dto.Messages;
import com.backend.demo.service.MessagesService;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Optional;

import static com.backend.demo.constant.PathConstant.URL_GET_ALL_MESSAGES;
import static com.backend.demo.constant.PathConstant.URL_POST_MESSAGE;

@RestController
public class MessagesController extends BaseControllerApi<MessagesService> {

    public MessagesController(MessagesService service) {
        super(service);
    }

    @CrossOrigin
    @GetMapping(path = URL_GET_ALL_MESSAGES, produces = {"application/json"})
    public Optional<Messages> message(@PathVariable @NotNull Integer id) {
        return getService().messages(id);
    }

    @CrossOrigin
    @PostMapping(path = URL_POST_MESSAGE, produces = {"application/json"})
    public Messages postMessage(@PathVariable @NotNull Integer id, @RequestBody @NotNull String message) {
        return getService().portMessage(id, message);
    }

}
