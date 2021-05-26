package com.backend.service;

import com.backend.dto.Info;
import com.backend.model.utils.UsersDtoJson;
import com.backend.dto.Users;
import com.backend.exceptions.NotFoundException;
import com.backend.exceptions.UnauthorizedException;
import com.backend.model.ProfileResponse;
import com.backend.model.UsersProfileEditRequest;
import com.backend.repository.UsersRepository;
import com.backend.utils.SessionUtil;
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

    public Users editProfile(UsersProfileEditRequest usersProfileEditRequest) {
        Users users = findUserById(SessionUtil.getCurrentUser().getUsersId());
        if (users.getInfo() == null) {
            Info info = new Info();
            info.setBio(usersProfileEditRequest.getBio());
            users.setInfo(info);
        } else {
            users.getInfo().setBio(usersProfileEditRequest.getBio());
        }
        usersRepository.save(users);
        return users;
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
        if (id.equals(SessionUtil.getCurrentUser().getUsersId())) {
            return followerService.isFollow(id, id);
        }
            return followerService.followerOptional(SessionUtil.getCurrentUser().getUsersId())
                    .stream()
                    .anyMatch(f -> f.getUsersId().equals(id));
    }
}
