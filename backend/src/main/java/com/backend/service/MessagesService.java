package com.backend.service;

import com.backend.dto.Messages;
import com.backend.dto.Users;
import com.backend.exceptions.NotFoundException;
import com.backend.exceptions.UnacceptableException;
import com.backend.exceptions.UnauthorizedException;
import com.backend.model.MessagesAllResponse;
import com.backend.model.MessagesPostRequest;
import com.backend.model.MessagesPostResponse;
import com.backend.model.utils.UsersDtoJson;
import com.backend.repository.MessagesRepository;
import com.backend.utils.SessionUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessagesService {

    private final MessagesRepository messagesRepository;
    private final UsersService usersService;

    public MessagesService(MessagesRepository messagesRepository, UsersService usersService) {
        this.messagesRepository = messagesRepository;
        this.usersService = usersService;
    }

    public List<MessagesAllResponse> messages(Integer id) {
        List<MessagesAllResponse> messagesAllResponseList = new ArrayList<>();
        messagesRepository.findMessagesByUsersId(id).forEach(m -> {
            Users users = usersService.findUserById(m.getUserMessageId());
            messagesAllResponseList.add(
                    new MessagesAllResponse(
                            users.getFirstName() + " " + users.getLastName(), m.getMessage()));
        });
        return messagesAllResponseList;
    }

    public MessagesPostResponse postMessage(MessagesPostRequest messagesPostRequest) {
        if (messagesPostRequest.getMessage().isBlank() || messagesPostRequest.getMessage().isEmpty()) {
            throw new UnacceptableException("The message can't be blank");
        }
        return createPost(
                usersService.usersDtoJsonByEmail(messagesPostRequest.getUsername()),
                messagesPostRequest.getMessage(),
                usersService.usersDtoJsonById(SessionUtil.getCurrentUser().getUsersId())
        );
    }

    private MessagesPostResponse createPost(UsersDtoJson user, String message, UsersDtoJson userMessage ) {
        Messages messages = new Messages();
        messages.setUsersId(user.getId());
        messages.setMessage(message);
        messages.setUserMessageId(userMessage.getId());
        messages = messagesRepository.save(messages);
        return new MessagesPostResponse(messages.getId(), user, messages.getMessage(), userMessage);
    }

    public void deleteMsg(Integer id) {
        Messages messages = messagesRepository.findMessageById(id)
                .orElseThrow(() -> {
                        throw new NotFoundException("The message id doesn't exist!");
                });
        isUserAllowedToDelete(messages);
    }

    private void isUserAllowedToDelete(Messages messages) {
        Integer currentUser = SessionUtil.getCurrentUser().getUsersId();
        if (!(messages.getUsersId().equals(currentUser) || messages.getUserMessageId().equals(currentUser))) {
            throw new UnauthorizedException("you don't have authorization to delete this message!");
        }
        messagesRepository.delete(messages);
    }
}
