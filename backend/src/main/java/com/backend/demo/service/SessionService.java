package com.backend.demo.service;

import com.backend.demo.config.token.CreateJWT;
import com.backend.demo.exceptions.NotFoundException;
import com.backend.demo.model.SessionRequest;
import com.backend.demo.dto.Session;
import com.backend.demo.dto.Users;
import com.backend.demo.exceptions.UnauthorizedException;
import com.backend.demo.repository.SessionRepository;
import com.backend.demo.config.security.EncryptSecret;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SessionService {

    private final SessionRepository sessionRepository;
    private final UsersService usersService;
    private final VerifyJWTService verifyJWTService;

    public SessionService(SessionRepository sessionRepository, UsersService usersService, VerifyJWTService verifyJWTService) {
        this.sessionRepository = sessionRepository;
        this.usersService = usersService;
        this.verifyJWTService = verifyJWTService;
    }

    public String findCredentialId(SessionRequest sessionRequest) {
        Users users = usersService.findUserByEmail(sessionRequest.getEmail());
        if (secretReview(users.getCredentials().getCredential(), sessionRequest.getSecret())) {
            return createSession(users.getId(), users.getFirstName() + " " + users.getLastName(), users.getEmail());
        }
        throw new UnauthorizedException("Authentication required");
    }

    private boolean secretReview(String credential, String secret) {
        String secureSecret = credential.split("(?<==)")[0];
        String salt = credential.split("(?<==)")[1];
        EncryptSecret encryptSecret = new EncryptSecret();
        return encryptSecret.verifyUserSecret(secret, secureSecret, salt);
    }

    private String createSession(int id, String name, String email) {
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
        session.setDate(verifyJWTService.getExp(jwtToken));
        sessionRepository.save(session);
        return jwtToken;
    }

    public void logout(String jwt) {
        Session session = findSessionByToken(jwt.substring(7));
        sessionDelete(session);
    }

    public Session findSessionByToken(String jwt) {
        return sessionRepository.findSessionByToken(jwt).orElseThrow(
                () -> new NotFoundException("the session not exist!")
        );
    }

    public void sessionDelete(Session session) {
        sessionRepository.delete(session);
    }
}
