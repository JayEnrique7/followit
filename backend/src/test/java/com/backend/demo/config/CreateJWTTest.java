package com.backend.demo.config;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
public class CreateJWTTest {
   @SpyBean
   CreateJWT createJWT;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void hejhej() {
        String iss = "issuer";
        String sub = "subject";

        System.out.println(System.currentTimeMillis());

        String he = createJWT.createJWT(1, iss, sub, 1616959266350123L);
        createJWT.getMapFromIoJsonWebTokenClaims(he).forEach(
                (key, value) -> {
                    if (key.equalsIgnoreCase("sub")) {
                        assertEquals(sub, value);
                    }
                    if (key.equalsIgnoreCase("iss")) {
                        assertEquals(iss, value);
                    }
                    if (key.equalsIgnoreCase("jti")) {
                        assertEquals("1", value);
                    }
                    if (key.equalsIgnoreCase("exp") || key.equalsIgnoreCase("iat")) {


                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                        Date date = new Date(Long.parseLong(value.toString()));
                        System.out.println(key + " " + formatter.format(date));
                    }

                });

    }


}
