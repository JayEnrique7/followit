package com.backend.demo.config;

import java.nio.charset.StandardCharsets;

import com.backend.demo.exceptions.UnexpectedError;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.Map;

import io.jsonwebtoken.Jwts;

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

    public String getSub(String jwt) {
        return getMapFromIoJsonWebTokenClaims(jwt, "sub");
    }

    public UUID getJti(String jwt) {
        return UUID.fromString(getMapFromIoJsonWebTokenClaims(jwt, "jti"));
    }

    public String getName(String jwt) {
        return getMapFromIoJsonWebTokenClaims(jwt, "name");
    }

    public boolean getAdmin(String jwt) {
        return Boolean.parseBoolean(getMapFromIoJsonWebTokenClaims(jwt, "admin"));
    }

    public Date getIat(String jwt) {
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
        throw new UnexpectedError("Unexpected error");
    }
}
