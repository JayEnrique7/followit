package com.backend.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@RestController
public class DemoApplication {
	//@Autowired
	//private MessageRepository messageRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@PostMapping("/message")
	public @ResponseBody String createNewMessage(@RequestParam String message) {
		Message newMessage = new Message(message);
		//messageRepository.save(newMessage);
		return "{\"success\": true}";
	}

	@GetMapping("/messages")
	public @ResponseBody Iterable<Message> getMessages() {
		List<Message> res = new LinkedList<>();
		res.add(new Message("Hello World"));
		return res;//messageRepository.findAll();
	}
}
