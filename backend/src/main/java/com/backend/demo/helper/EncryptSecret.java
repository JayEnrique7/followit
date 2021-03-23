package com.backend.demo.helper;

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

    public static String getSalt(int length) {
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }

    public static byte[] hash(char[] secret, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(secret, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(secret, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing the secret: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    public static String generateSecurePassword(String secret, String salt) {
        String returnValue = null;
        byte [] secureSecret = hash(secret.toCharArray(), salt.getBytes());
        returnValue = Base64.getEncoder().encodeToString(secureSecret);
        return returnValue;
    }

    public static boolean verifyUserSecret(String provideSecret, String secureSecret, String salt) {
        boolean returnValue = false;
        String newSecurePassword = generateSecurePassword(provideSecret, salt);
        returnValue = newSecurePassword.equalsIgnoreCase(secureSecret);
        return returnValue;
    }

}
