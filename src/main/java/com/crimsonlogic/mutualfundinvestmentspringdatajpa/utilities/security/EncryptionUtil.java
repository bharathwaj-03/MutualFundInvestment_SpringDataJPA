package com.crimsonlogic.mutualfundinvestmentspringdatajpa.utilities.security;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Provides reversible encryption for sensitive application data.
 *
 * AES in GCM mode is used so confidential values can be encrypted before
 * persistence and decrypted only when the application needs to display or
 * process the original value.
 */
public class EncryptionUtil {

    /**
     * Secret material used to derive the AES key for this training project.
     *
     * In a production application, secret material should be supplied from a
     * protected external source such as an environment variable or secret
     * management system rather than being stored directly in source code.
     */
    private static final String SECRET_KEY =
            "MutualFundInvestment@2026SecureKey";


    /** AES-GCM transformation used for authenticated encryption. */
    private static final String ALGORITHM =
            "AES/GCM/NoPadding";

    /** Authentication tag length, in bits, used by GCM. */
    private static final int GCM_TAG_LENGTH =
            128;

    /** Initialization-vector length, in bytes, generated for each encryption. */
    private static final int IV_LENGTH =
            12;


    /**
     * Derives the AES key used by both encryption and decryption.
     *
     * SHA-256 converts the configured secret into fixed-length key material,
     * ensuring both operations derive the same AES key.
     *
     * @return AES secret key specification
     * @throws Exception if the required cryptographic algorithm is unavailable
     */
    private static SecretKeySpec getSecretKey()
            throws Exception {

        MessageDigest digest =
                MessageDigest.getInstance("SHA-256");

        byte[] key =
                digest.digest(
                        SECRET_KEY.getBytes(
                                StandardCharsets.UTF_8));

        return new SecretKeySpec(
                key,
                "AES");
    }


    /**
     * Encrypts a sensitive string using AES-GCM.
     *
     * A fresh initialization vector is generated for every encryption. The IV
     * and ciphertext are Base64 encoded and stored together so the same IV is
     * available when the value is later decrypted. Null or blank values are
     * returned unchanged.
     *
     * @param value plain-text value to encrypt
     * @return encoded IV and encrypted value in a single string
     * @throws RuntimeException if encryption cannot be completed
     */
    public static String encrypt(String value) {

        try {

            if (value == null ||
                    value.trim().isEmpty()) {

                return value;
            }

            byte[] iv =
                    new byte[IV_LENGTH];

            SecureRandom secureRandom =
                    new SecureRandom();

            secureRandom.nextBytes(iv);


            Cipher cipher =
                    Cipher.getInstance(
                            ALGORITHM);


            GCMParameterSpec spec =
                    new GCMParameterSpec(
                            GCM_TAG_LENGTH,
                            iv);


            cipher.init(
                    Cipher.ENCRYPT_MODE,
                    getSecretKey(),
                    spec);


            byte[] encrypted =
                    cipher.doFinal(
                            value.getBytes(
                                    StandardCharsets.UTF_8));


            /*
             * The initialization vector is stored beside the ciphertext because
             * decryption must use the same IV that was created during encryption.
             */

            return Base64.getEncoder()
                    .encodeToString(iv)
                    + ":"
                    + Base64.getEncoder()
                    .encodeToString(encrypted);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to encrypt sensitive data.",
                    e);
        }
    }


    /**
     * Restores the original value from data produced by {@link #encrypt(String)}.
     *
     * The stored value is separated into its Base64-encoded initialization
     * vector and ciphertext before AES-GCM decryption is performed. Null or
     * blank values are returned unchanged.
     *
     * @param encryptedValue encoded encrypted value to decrypt
     * @return original plain-text value
     * @throws RuntimeException if the encrypted format is invalid or decryption fails
     */
    public static String decrypt(String encryptedValue) {

        try {

            if (encryptedValue == null ||
                    encryptedValue.trim().isEmpty()) {

                return encryptedValue;
            }


            String[] parts =
                    encryptedValue.split(":");


            if (parts.length != 2) {

                throw new IllegalArgumentException(
                        "Invalid encrypted value.");
            }


            byte[] iv =
                    Base64.getDecoder()
                            .decode(parts[0]);


            byte[] encrypted =
                    Base64.getDecoder()
                            .decode(parts[1]);


            Cipher cipher =
                    Cipher.getInstance(
                            ALGORITHM);


            GCMParameterSpec spec =
                    new GCMParameterSpec(
                            GCM_TAG_LENGTH,
                            iv);


            cipher.init(
                    Cipher.DECRYPT_MODE,
                    getSecretKey(),
                    spec);


            byte[] decrypted =
                    cipher.doFinal(encrypted);


            return new String(
                    decrypted,
                    StandardCharsets.UTF_8);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to decrypt sensitive data.",
                    e);
        }
    }
}
