package com.backend.demo.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
        String secret = "hejhej";

        // Salt value stored in database
        String salt = encryptSecret.getSalt(50);



        // Encrypted and Base64 encoded password read from database
        String secureSecret = encryptSecret.generateSecureSecret(secret, salt);

        /*String pass = "VmsHYtGwZKcOkK3jc3CjgIbCCLklE0DPwdv53IpuYxY=2b2zDcuXaQzp0emsT1fe06z241jg61CeLbELTa5PUySZey1234";
        String se = pass.split("(?<==)")[0];
        String sa = pass.split("(?<==)")[1];*/

        //System.out.println(securePassword);
        //System.out.println(salt);

        boolean passwordMatch = encryptSecret.verifyUserSecret(secret, secureSecret, salt);

        assertTrue(passwordMatch);

    }

}
