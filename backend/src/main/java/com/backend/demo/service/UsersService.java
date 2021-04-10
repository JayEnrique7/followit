package com.backend.demo.service;

import com.backend.demo.dto.Users;
import com.backend.demo.exceptions.NotFoundException;
import com.backend.demo.exceptions.UnauthorizedException;
import com.backend.demo.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.stream.StreamSupport;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Users findUserByEmail(String email) {
        return usersRepository.findUsersByEmail(email)
                .orElseThrow(() -> {
                    throw new UnauthorizedException("Authentication required");}
                    );
    }

    public Users findUserById(Integer id) {
        return usersRepository.findUsersById(id).orElseThrow(
                () -> new NotFoundException("the session not exist!")
        );
    }

}
