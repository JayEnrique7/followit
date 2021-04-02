package com.backend.demo.service;

import com.backend.demo.dto.Users;
import com.backend.demo.exceptions.UnauthorizedException;
import com.backend.demo.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Users findUserByEmail(String email) {
        if (usersRepository.findUserByEmail(email).iterator().hasNext()) {
            return usersRepository.findUserByEmail(email).iterator().next();
        }
        throw new UnauthorizedException("Authentication required");
    }

    public Optional<Users> findUserById(Long id) {
        return usersRepository.findById(id);
    }

}
