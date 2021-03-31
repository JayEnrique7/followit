package com.backend.demo.service;

import com.backend.demo.config.CreateJWT;
import com.backend.demo.domain.SessionDomain;
import com.backend.demo.dto.Session;
import com.backend.demo.dto.User;
import com.backend.demo.exceptions.ResourceNotFoundException;
import com.backend.demo.exceptions.UnauthorizedException;
import com.backend.demo.repository.SessionRepository;
import com.backend.demo.repository.UserRepository;
import com.backend.demo.validation.EncryptSecret;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SessionService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    public SessionService(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    public Session findCredentialId(SessionDomain sessionDomain) {
        User user = findUser(sessionDomain.getEmail());
        if (secretReview(user.getCredentials().getCredential(), sessionDomain.getSecret())) {
            createSession(user.getId(), user.getFirstName() + " " + user.getLastName(), user.getEmail());
            return sessionRepository.findSessionByUserId(user.getId()).stream().iterator().next();
        }
        throw new ResourceNotFoundException("The data does not found");
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

    private void createSession(int id, String name, String email) {
        sessionRepository.findSessionByUserId(id).ifPresent(sessionRepository::delete);
        String tokenUuid = UUID.randomUUID().toString();
        Iterable<Session> tokenUUID;
        while (true) {
            tokenUUID = sessionRepository.findSessionByUuid(tokenUuid);
            if(!tokenUUID.iterator().hasNext()) {
                break;
            }
            tokenUuid = UUID.randomUUID().toString();
        }

        CreateJWT createJWT = new CreateJWT();
        String jwtToken = createJWT.createJWT(tokenUuid, false, name, email);

        Session session = new Session();
        session.setUuid(tokenUuid);
        session.setEmail(email);
        session.setUserId(id);
        session.setToken(jwtToken);
        session.setDate(createJWT.getExp(jwtToken));
        sessionRepository.save(session);
    }
}
