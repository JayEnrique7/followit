package com.backend.service;

import com.backend.dto.Messages;
import com.backend.exceptions.NotFoundException;
import com.backend.exceptions.UnacceptableException;
import com.backend.exceptions.UnauthorizedException;
import com.backend.model.MessagesPostRequest;
import com.backend.repository.MessagesRepository;
import com.backend.utils.SessionUtil;
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
