package com.backend.demo.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

@RunWith(SpringJUnit4ClassRunner.class)
public class EncryptSecretTest {

    @SpyBean
    EncryptSecret encryptSecret;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void generateCrypto() {
        ArrayList<String> se = new ArrayList<>();
        ArrayList<String> sa = new ArrayList<>();

        String secret = "hejhej";

        for(int i = 0; i < 10; i++) {
            String salt = encryptSecret.getSalt(30);

            sa.add(salt);
            se.add(encryptSecret.generateSecurePassword(secret, salt));
        }

        for (String s : sa) {
            System.out.println(s + "   SALT");
        }

        for (String s : se) {
            System.out.println(s + "   SECRET");
        }


        /*String secret = "hejhej";
        String salt = encryptSecret.getSalt(30);
        String mySecurePassword = encryptSecret.generateSecurePassword(secret, salt);

        System.out.println(mySecurePassword);
        System.out.println(salt);*/
        /*String providedPassword = "hejhej";

        // Encrypted and Base64 encoded password read from database
        String securePassword = "G8z7le5NQcx60pJTV+REz+mRW+HrWaaxwZHfYhZZV7o=";

        // Salt value stored in database
        String salt = "WkR4kFJYlHTAzVIHgVsHKeZKvX4lp4";

        boolean passwordMatch = encryptSecret.verifyUserSecret(providedPassword, securePassword, salt);

        assertTrue(passwordMatch);*/

    }

}
