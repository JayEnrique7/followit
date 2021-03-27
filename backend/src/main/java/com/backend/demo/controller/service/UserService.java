package com.backend.demo.controller.service;

import com.backend.demo.controller.domain.UserLogin;
import com.backend.demo.dto.Credential;
import com.backend.demo.dto.User;
import com.backend.demo.dto.repository.CredentialRepository;
import com.backend.demo.dto.repository.UserRepository;
import com.backend.demo.exceptions.ResourceNotFoundException;
import com.backend.demo.exceptions.UnauthorizedException;
import com.backend.demo.helper.EncryptSecret;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final CredentialRepository credentialRepository;

    public UserService(UserRepository userRepository, CredentialRepository credentialRepository) {
        this.userRepository = userRepository;
        this.credentialRepository = credentialRepository;
    }

    public List<User> findCredentialId(UserLogin userLogin) {
        User user = findUser(userLogin.getEmail());
        Credential credential = credentialRepository.findCredentialById(user.getCredentialId()).iterator().next();
        if (secretReview(credential.getCredential(), userLogin.getSecret())) {
            return userRepository.findUserById(user.getId());
        }
        throw new ResourceNotFoundException("The data   does not found");
        //throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }

    private User findUser(String email) {
       if (userRepository.findUserByEmail(email).iterator().hasNext()) {
           return userRepository.findUserByEmail(email).iterator().next();
       }
        throw new UnauthorizedException();
    }

    private boolean secretReview(String credential, String secret) {
        String secureSecret = credential.split("(?<==)")[0];
        String salt = credential.split("(?<==)")[1];
        EncryptSecret encryptSecret = new EncryptSecret();
        return encryptSecret.verifyUserSecret(secret, secureSecret, salt);
    }

}
