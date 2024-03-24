package com.mike.projects.fitsynergy.users.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class BCryptUtil {
    public static String encrypt(String text){
        String salt = BCrypt.gensalt(10);
        return BCrypt.hashpw(text, salt);
    }

    public static boolean check(String hash, String text){
        boolean verified = false;

        if(hash != null && hash.startsWith("$2a$")){
            verified = BCrypt.checkpw(text, hash);
        }

        return verified;
    }
}
