package com.backend.demo.service;

import com.backend.demo.controller.UsersDtoJson;
import com.backend.demo.dto.Users;
import com.backend.demo.exceptions.NotFoundException;
import com.backend.demo.exceptions.UnauthorizedException;
import com.backend.demo.model.ProfileResponse;
import com.backend.demo.repository.UsersRepository;
import com.backend.demo.utils.SessionUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final FollowerService followerService;

    @Lazy
    public UsersService(UsersRepository usersRepository, FollowerService followerService) {
        this.usersRepository = usersRepository;
        this.followerService = followerService;
    }

    public ProfileResponse profileResponse(Integer id) {
        return new ProfileResponse(usersDtoJsonById(id), userIsFollower(id));
    }

    public Users findUserByEmail(String email) {
        return usersRepository.findUsersByEmail(email)
                .orElseThrow(() -> {
                    throw new UnauthorizedException("the user doesn't exist!");}
                    );
    }

    public Users findUserById(Integer id) {
        return usersRepository.findUsersById(id).orElseThrow(
                () -> new NotFoundException("the user doesn't exist!")
        );
    }

    public UsersDtoJson usersDtoJsonByEmail(String email) {
        Users users = findUserByEmail(email);
        return new UsersDtoJson(users.getId(), users.getEmail(), users.getFirstName(), users.getLastName(), users.getInfo());
    }

    public UsersDtoJson usersDtoJsonById(Integer id) {
        Users users = findUserById(id);
        return new UsersDtoJson(users.getId(), users.getEmail(), users.getFirstName(), users.getLastName(), users.getInfo());
    }

    public Boolean userIsFollower(Integer id) {
        return followerService.followerOptional(SessionUtil.getCurrentUser().getUsersId())
                .stream()
                .anyMatch(f -> f.getUsersId().equals(id));
    }
}
