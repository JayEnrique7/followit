package com.backend.demo.service;

import com.backend.demo.dto.Messages;
import com.backend.demo.repository.MessagesRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessagesService {

    private final MessagesRepository messagesRepository;

    public MessagesService(MessagesRepository messagesRepository) {
        this.messagesRepository = messagesRepository;
    }

    public Optional<Messages> messages(Integer id) {
        return messagesRepository.findByUserId(id);
    }
}
