package com.backend.demo.repository;

import com.backend.demo.dto.Messages;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MessagesRepository extends CrudRepository<Messages, Integer> {
    Optional<Messages> findMessagesByUsersId(Integer id);
}
