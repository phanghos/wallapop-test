package org.taitasciore.android.api;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by roberto on 21/03/17.
 */

/**
 * Helper class with md5-related methods
 */
public final class ApiUtils {

    /**
     * Encrypts a string using md5
     * @param str String to encrypt
     * @return Encrypted string
     */
    private static String md5(String str) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("md5");
            digest.update(str.getBytes());
            final byte[] bytes = digest.digest();
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) sb.append(String.format("%02X", bytes[i]));
            return sb.toString().toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * Generates hash required by the Marvel API, which must be of the form
     * md5(timestamp + private key + public key).
     * @param ts Timestamp
     * @return Encrypted string that will be sent as a query parameter in the HTTP request
     * to the API
     */
    public static String generateHash(long ts) {
        return md5(ts + MarvelApi.PRIVATE_KEY + MarvelApi.PUBLIC_KEY);
    }
}
