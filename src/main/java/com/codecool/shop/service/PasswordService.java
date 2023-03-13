package com.codecool.shop.service;
import org.mindrot.jbcrypt.BCrypt;
public class PasswordService {
    public static String hashPassword(String password) {
        int workload = 12;
        String salt = BCrypt.gensalt(workload);
        return BCrypt.hashpw(password, salt);
    }

    public static boolean checkPassword(String password, String storedHash) {
        boolean passwordVerified;
        if (null == storedHash || !storedHash.startsWith("$2a$"))
            throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");
        passwordVerified = BCrypt.checkpw(password, storedHash);
        return passwordVerified;
    }
}
