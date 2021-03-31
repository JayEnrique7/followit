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
        UUID id = UUID.randomUUID();
        String sub = "glenn.quagmire@mail.com";
        String name = "Glenn Quagmire";

        String jwtToken = createJWT.createJWT(id.toString(), false, name, sub);

        Date iat = createJWT.getIat(jwtToken);
        Date exp = createJWT.getExp(jwtToken);

        long diffInMillis = Math.abs(exp.getTime() - iat.getTime());
        int diff = (int) TimeUnit.HOURS.convert(diffInMillis, TimeUnit.MILLISECONDS);

        assertEquals(sub, createJWT.getSub(jwtToken));
        assertEquals(id, createJWT.getJti(jwtToken));
        assertEquals(name, createJWT.getName(jwtToken));
        assertFalse(createJWT.getAdmin(jwtToken));
        assertThat(iat.before(exp), is(true));
        assertEquals(12, diff);
    }
}
