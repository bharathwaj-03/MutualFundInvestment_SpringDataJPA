package com.crimsonlogic.mutualfundinvestmentspringdatajpa.utilities.security;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Provides password hashing and verification operations using BCrypt.
 *
 * Passwords are stored as one-way hashes rather than plain text. During
 * authentication, the supplied password is compared with the stored hash.
 */
public class PasswordUtil {

    /**
     * Converts a plain-text password into a BCrypt hash.
     *
     * BCrypt generates a salt as part of the hashing operation so the stored
     * value can be verified later without storing the original password.
     *
     * @param password plain-text password to hash
     * @return BCrypt hash suitable for persistence
     */
    public static String hashPassword(
            String password){

        return BCrypt.hashpw(
                password,
                BCrypt.gensalt());
    }

    /**
     * Verifies whether a plain-text password matches a stored BCrypt hash.
     *
     * @param password plain-text password supplied for authentication
     * @param hashedPassword previously stored BCrypt password hash
     * @return true when the supplied password matches the stored hash;
     * otherwise false
     */
    public static boolean verifyPassword(
            String password,
            String hashedPassword){

        return BCrypt.checkpw(
                password,
                hashedPassword);
    }

}
