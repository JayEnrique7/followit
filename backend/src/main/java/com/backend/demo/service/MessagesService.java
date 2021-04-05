package com.backend.demo.service;

import com.backend.demo.dto.Messages;
import com.backend.demo.dto.Users;
import com.backend.demo.exceptions.NotFoundException;
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
        Users users = usersService.findUserById(id);
        System.out.println(SessionUtil.getCurrentUser().getUsers().getId());
        Optional<Messages> messages = messagesRepository.findMessagesByUsersId(users);
        if(messages.isPresent()) {
            return messages;
        }
        throw new NotFoundException("Empty messages!");
    }

    public Messages portMessage(Integer id, String message) {
        Messages messages = new Messages();
        return messages;
    }
}
