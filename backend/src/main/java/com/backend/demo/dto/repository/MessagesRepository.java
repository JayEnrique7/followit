package com.backend.demo.dto.repository;

import com.backend.demo.dto.Messages;
import org.springframework.data.repository.CrudRepository;

public interface MessagesRepository extends CrudRepository<Messages, Integer> {
}
