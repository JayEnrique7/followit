package com.backend.config.token;

import java.nio.charset.StandardCharsets;

import com.backend.constant.PathConstant;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.*;

import io.jsonwebtoken.Jwts;

public class CreateJWT {

    public String createJWT(String id, boolean admin, String name, String subject) {

        return Jwts.builder()
                .setSubject(subject)
                .setId(id)
                .setIssuedAt(addHours(0))
                .setExpiration(addHours(12))
                .claim("name", name)
                .claim("admin", admin)
                .signWith(SignatureAlgorithm.HS256, PathConstant.SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    private Date addHours(int hours) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.HOUR, hours);
        return now.getTime();
    }
}
