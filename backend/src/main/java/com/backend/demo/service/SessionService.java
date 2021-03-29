package com.backend.demo.service;

import com.backend.demo.config.CreateJWT;
import com.backend.demo.domain.SessionDomain;
import com.backend.demo.dto.Credential;
import com.backend.demo.dto.Token;
import com.backend.demo.dto.User;
import com.backend.demo.exceptions.ResourceNotFoundException;
import com.backend.demo.exceptions.UnauthorizedException;
import com.backend.demo.repository.CredentialRepository;
import com.backend.demo.repository.TokenRepository;
import com.backend.demo.repository.UserRepository;
import com.backend.demo.validation.EncryptSecret;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
public class SessionService {

    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;
    private final TokenRepository tokenRepository;

    public SessionService(UserRepository userRepository, CredentialRepository credentialRepository,
                          TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.credentialRepository = credentialRepository;
        this.tokenRepository = tokenRepository;
    }

    public List<User> findCredentialId(SessionDomain sessionDomain) {
        User user = findUser(sessionDomain.getEmail());
        Credential credential = credentialRepository.findCredentialById(user.getCredentialId()).iterator().next();
        if (secretReview(credential.getCredential(), sessionDomain.getSecret())) {
            createSession(user.getId(), user.getFirstName(), user.getEmail());
            return userRepository.findUserById(user.getId());
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
        tokenRepository.findTokenByUserId(id).ifPresent(tokenRepository::delete);
        String tokenId = UUID.randomUUID().toString();
        Iterable<Token> tokenUUID;
        while (true) {
            tokenUUID = tokenRepository.findTokenByTokenId(tokenId);
            if(!tokenUUID.iterator().hasNext()) {
                break;
            }
            tokenId = UUID.randomUUID().toString();
        }

        Calendar now = Calendar.getInstance();
        now.add(Calendar.HOUR, 12);
        Date date = now.getTime();

        CreateJWT createJWT = new CreateJWT();

        Token token = new Token();

        token.setDate(date);
        token.setTokenId(tokenId);
        token.setUserId(id);
        token.setToken(createJWT.createJWT(tokenId, false, name, email));
        tokenRepository.save(token);
    }
}
