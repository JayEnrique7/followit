package com.backend.demo.service;

import com.backend.demo.exceptions.UnexpectedErrorException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

import static com.backend.demo.constant.PathConstant.SECRET_KEY;

@Service
public class VerifyJWTService {

    private Claims decodeJWT(String jwt) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(jwt).getBody();
    }

    public String getSub(String jwt) {
        return getMapFromIoJsonWebTokenClaims(jwt, "sub");
    }

    private boolean getAdmin(String jwt) {
        return Boolean.parseBoolean(getMapFromIoJsonWebTokenClaims(jwt, "admin"));
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
