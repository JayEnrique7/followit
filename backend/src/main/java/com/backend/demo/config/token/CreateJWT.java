package com.backend.demo.config.token;

import java.nio.charset.StandardCharsets;

import com.backend.demo.dto.Session;
import com.backend.demo.dto.Users;
import com.backend.demo.exceptions.UnauthorizedException;
import com.backend.demo.exceptions.UnexpectedErrorException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.*;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

@Service
public class CreateJWT {

    private static final String SECRET_KEY = "oeRaYY7Wo24sDqKSX3IM9ASGmdGPmkTd9jo1QTy4b7P9Ze5_9hKolVX8xNrQDcNRfVEdTZNOuOyqEGhXEbdJI-ZQ19k_o9MI0y3eZN2lp9jow55FfXMiINEdt1XR85VipRLSOkT6kSpzs2x-jbLDiz9iFVzkd81YKxMgPA7VfZeQUm4n-mOmnWMaVX30zGFU4L3oPBctYKkl4dYfqYWqRNfrgPJVi5DGFjywgxx0ASEiJHtV72paI3fDR2XwlSkyhhmY-ICjCRmsJN4fX1pdoL8a18-aQrvyu4j0Os6dVPYIoPvvY0SAZtWYKHfM15g7A3HD4cVREf9cUsprCRK93w";

    public String createJWT(String id, boolean admin, String name, String subject) {

        return Jwts.builder()
                .setSubject(subject)
                .setId(id)
                .setIssuedAt(addHours(0))
                .setExpiration(addHours(12))
                .claim("name", name)
                .claim("admin", admin)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    private Date addHours(int hours) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.HOUR, hours);
        return now.getTime();
    }

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
