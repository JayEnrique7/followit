package com.backend.demo.service;

import com.backend.demo.dto.Messages;
import com.backend.demo.exceptions.NotFoundException;
import com.backend.demo.exceptions.UnacceptableException;
import com.backend.demo.model.MessagesPostRequest;
import com.backend.demo.repository.MessagesRepository;
import com.backend.demo.utils.SessionUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessagesService {

    private final MessagesRepository messagesRepository;
    private final UsersService usersService;

    public MessagesService(MessagesRepository messagesRepository, UsersService usersService) {
        this.messagesRepository = messagesRepository;
        this.usersService = usersService;
    }

    public Optional<Messages> messages(Integer id) {
        Optional<Messages> messages = messagesRepository.findMessagesByUsersId(id);
        if(messages.isPresent()) {
            return messages;
        }
        throw new NotFoundException("Empty messages!");
    }

    public Messages postMessage(MessagesPostRequest messagesPostRequest) {
        if (messagesPostRequest.getMessage().isBlank() || messagesPostRequest.getMessage().isEmpty()) {
            throw new UnacceptableException("The message can't be blank");
        }
        return createPost(usersService.findUserByEmail(messagesPostRequest.getUsername()).getId(), messagesPostRequest.getMessage(), SessionUtil.getCurrentUser().getUsersId());
    }

    private Messages createPost(Integer userId, String message, Integer userMessageId ) {
        Messages messages = new Messages();
        messages.setUsersId(userId);
        messages.setMessage(message);
        messages.setUserMessageId(userMessageId);
        return messagesRepository.save(messages);
    }
}
