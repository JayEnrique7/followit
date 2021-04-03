package com.backend.demo.service;

import com.backend.demo.dto.Session;
import com.backend.demo.dto.Users;
import com.backend.demo.exceptions.UnauthorizedException;
import com.backend.demo.exceptions.UnexpectedErrorException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import static com.backend.demo.constant.PathConstant.SECRET_KEY;

@Service
public class VerifyJWTService {

    private Claims decodeJWT(String jwt) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(jwt).getBody();
    }

    public boolean validateToken(String token, Users users, Session session) {
        boolean id = users.getId().equals(session.getUserId());
        boolean email = users.getEmail().equalsIgnoreCase(getSub(token));
        boolean userName = (users.getFirstName() + " " + users.getLastName()).equalsIgnoreCase(getName(token));
        if (id && email && userName) {
            return true;
        }
        throw new UnauthorizedException("Invalid token");
    }

    public String getSub(String jwt) {
        return getMapFromIoJsonWebTokenClaims(jwt, "sub");
    }

    private UUID getJti(String jwt) {
        return UUID.fromString(getMapFromIoJsonWebTokenClaims(jwt, "jti"));
    }

    public String getName(String jwt) {
        return getMapFromIoJsonWebTokenClaims(jwt, "name");
    }

    private boolean getAdmin(String jwt) {
        return Boolean.parseBoolean(getMapFromIoJsonWebTokenClaims(jwt, "admin"));
    }

    private Date getIat(String jwt) {
        return new Date(Long.parseLong(getMapFromIoJsonWebTokenClaims(jwt, "iat"))*1000);
    }

    public Date getExp(String jwt) {
        return new Date(Long.parseLong(getMapFromIoJsonWebTokenClaims(jwt, "exp"))*1000);
    }

    private String getMapFromIoJsonWebTokenClaims(String jwt, String k){
        Claims claims = decodeJWT(jwt);
        for(Map.Entry<String, Object> entry : claims.entrySet()) {
            if(entry.getKey().equalsIgnoreCase(k)) {
                return entry.getValue().toString();
            }
        }
        throw new UnexpectedErrorException("Unexpected error");
    }
}
