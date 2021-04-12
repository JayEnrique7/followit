package com.backend.controller;

import com.backend.controller.api.BaseControllerApi;
import com.backend.dto.Messages;
import com.backend.model.MessagesPostRequest;
import com.backend.service.MessagesService;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Optional;

import static com.backend.constant.PathConstant.*;

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
    public Messages postMessage(@RequestBody @NotNull MessagesPostRequest messagesPostRequest) {
        return getService().postMessage(messagesPostRequest);
    }

    @CrossOrigin
    @DeleteMapping(path = URL_DELETE_MESSAGE, produces = {"application/json"})
    public void deleteMessage(@PathVariable @NotNull Integer id) {
        getService().deleteMsg(id);
    }

}
