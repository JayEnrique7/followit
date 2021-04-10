package com.backend.demo.controller;

import com.backend.demo.controller.api.BaseControllerApi;
import com.backend.demo.controller.views.MessageView;
import com.backend.demo.dto.Messages;
import com.backend.demo.model.MessagesPostRequest;
import com.backend.demo.service.MessagesService;
import com.fasterxml.jackson.annotation.JsonView;
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
    @JsonView(MessageView.Message.class)
    @PostMapping(path = URL_POST_MESSAGE, produces = {"application/json"})
    public Messages postMessage(@RequestBody @NotNull MessagesPostRequest messagesPostRequest) {
        return getService().postMessage(messagesPostRequest);
    }

}
