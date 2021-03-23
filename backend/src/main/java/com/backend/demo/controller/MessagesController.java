package com.backend.demo.controller;

import com.backend.demo.dto.Messages;
import com.backend.demo.dto.repository.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/")
public class MessagesController {

    @Autowired
    MessagesRepository messagesRepository;

    @GetMapping(path = "messages/all")
    public @ResponseBody Iterable<Messages> getAllMessages() {
        return messagesRepository.findAll();
    }

}
