package com.backend.service;

import com.backend.config.security.EncryptSecret;
import com.backend.config.token.CreateJWT;
import com.backend.config.token.VerifyJWTService;
import com.backend.exceptions.NotFoundException;
import com.backend.model.SessionRequest;
import com.backend.dto.Session;
import com.backend.dto.Users;
import com.backend.exceptions.UnauthorizedException;
import com.backend.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SessionService {

    @Autowired
    UserDetailsService userDetailsService;
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
            return createSession(users);
        }
        throw new UnauthorizedException("Authentication required");
    }

    public boolean secretReview(String credential, String secret) {
        String secureSecret = credential.split("(?<==)")[0];
        String salt = credential.split("(?<==)")[1];
        EncryptSecret encryptSecret = new EncryptSecret();
        return encryptSecret.verifyUserSecret(secret, secureSecret, salt);
    }

    private String createSession(Users users) {
        sessionRepository.findSessionByUsersId(users.getId()).ifPresent(sessionRepository::delete);
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
        String jwtToken = createJWT.createJWT(tokenUuid, false, users.getFirstName() + " " + users.getLastName(), users.getEmail());

        Session session = new Session();
        session.setUuid(tokenUuid);
        session.setEmail(users.getEmail());
        session.setUsersId(users.getId());
        session.setToken(jwtToken);
        session.setDate(verifyJWTService.getExp(jwtToken));
        sessionRepository.save(session);
        return jwtToken;
    }

    public void logout(String jwt) {
        System.out.println(userDetailsService);
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

    public Session findUuid(String uuid) {
        Session session = sessionRepository.findSessionByUuid(uuid).iterator().next();
        if (session != null) {
            return session;
        }
        throw new UnauthorizedException("Authentication required");
    }

    public Session findSessionByUser(Integer userId) {
        return sessionRepository.findSessionByUsersId(userId).orElseThrow(
                () -> new NotFoundException("the session not exist!")
        );
    }
}
