package com.backend.demo.validation;

import com.backend.demo.exceptions.UnexpectedError;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class EncryptSecret {

    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int ITERATIONS = 1000;
    private static final int KEY_LENGTH = 256;

    public String getSalt(int length) {
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }

    public byte[] hash(char[] secret, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(secret, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(secret, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new UnexpectedError("Unexpected error");
        } finally {
            spec.clearPassword();
        }
    }

    public String generateSecureSecret(String secret, String salt) {
        String returnValue = null;
        byte [] secureSecret = hash(secret.toCharArray(), salt.getBytes());
        returnValue = Base64.getEncoder().encodeToString(secureSecret);
        return returnValue;
    }

    public boolean verifyUserSecret(String provideSecret, String secureSecret, String salt) {
        boolean returnValue = false;
        String newSecurePassword = generateSecureSecret(provideSecret, salt);
        returnValue = newSecurePassword.equalsIgnoreCase(secureSecret);
        return returnValue;
    }

}
