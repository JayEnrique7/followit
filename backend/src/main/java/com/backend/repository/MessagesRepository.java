package com.backend.repository;

import com.backend.dto.Messages;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MessagesRepository extends CrudRepository<Messages, Integer> {
    List<Messages> findMessagesByUsersId(Integer id);
    Optional<Messages> findMessageById(Integer id);
}
