package com.backend.config;

import static org.junit.Assert.assertTrue;

import com.backend.config.security.EncryptSecret;
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
        String salt = encryptSecret.getSalt(50);
        String secureSecret = encryptSecret.generateSecureSecret(secret, salt);
        assertTrue(encryptSecret.verifyUserSecret(secret, secureSecret, salt));
    }

}
