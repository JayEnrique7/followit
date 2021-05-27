package com.backend.controller;

import com.backend.controller.api.BaseControllerApi;
import com.backend.dto.Messages;
import com.backend.model.MessagesAllResponse;
import com.backend.model.MessagesPostRequest;
import com.backend.model.MessagesPostResponse;
import com.backend.service.MessagesService;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

import static com.backend.constant.PathConstant.*;

@RestController
@CrossOrigin
public class MessagesController extends BaseControllerApi<MessagesService> {

    public MessagesController(MessagesService service) {
        super(service);
    }

    @GetMapping(path = URL_GET_ALL_MESSAGES, produces = {"application/json"})
    public List<MessagesAllResponse> message(@PathVariable @NotNull Integer id) {
        return getService().messages(id);
    }

    @PostMapping(path = URL_POST_MESSAGE, produces = {"application/json"})
    public MessagesPostResponse postMessage(@RequestBody @NotNull MessagesPostRequest messagesPostRequest) {
        return getService().postMessage(messagesPostRequest);
    }

    @DeleteMapping(path = URL_DELETE_MESSAGE, produces = {"application/json"})
    public void deleteMessage(@PathVariable @NotNull Integer id) {
        getService().deleteMsg(id);
    }

}
