package com.backend.demo.config;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class CreateJWTTest {
   @SpyBean
   CreateJWT createJWT;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createJWT_Test() {
        String id = UUID.randomUUID().toString();
        String sub = "quagmire@mail.com";
        String name = "Quagmire";
        Date iat = new Date();
        Date exp = new Date();

        String jwtToken = createJWT.createJWT(id, false, name, sub);

        System.out.println(jwtToken);

        createJWT.getMapFromIoJsonWebTokenClaims(jwtToken).forEach(
                (key, value) -> {
                    if (key.equalsIgnoreCase("sub")) {
                        assertEquals(sub, value);
                    }
                    if (key.equalsIgnoreCase("jti")) {
                        assertEquals(id, value);
                    }
                    if (key.equalsIgnoreCase("name")) {
                        assertEquals(name, value);
                    }
                    if (key.equalsIgnoreCase("admin")) {
                        assertFalse(Boolean.parseBoolean(value.toString()));
                    }
                    if(key.equalsIgnoreCase("iat")) {
                        iat.setTime(Long.parseLong(value.toString())*1000);
                    }
                    if (key.equalsIgnoreCase("exp")) {
                        exp.setTime(Long.parseLong(value.toString())*1000);
                    }
                });

        long diffInMillis = Math.abs(exp.getTime() - iat.getTime());
        int diff = (int) TimeUnit.HOURS.convert(diffInMillis, TimeUnit.MILLISECONDS);

        assertThat(iat.before(exp), is(true));
        assertEquals(12, diff);
    }
}
