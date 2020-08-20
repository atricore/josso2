package org.atricore.josso.izpack;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PasswordUtil {

    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String OTHER_CHAR = "!@#$%&*()_+-=[]?";

    private static final String PASSWORD_ALLOW_BASE = CHAR_LOWER + CHAR_UPPER + NUMBER + OTHER_CHAR;
    private static final String PASSWORD_ALLOW_BASE_SHUFFLE = shuffleString(PASSWORD_ALLOW_BASE);
    private static final String PASSWORD_ALLOW = PASSWORD_ALLOW_BASE_SHUFFLE;

    private static SecureRandom random = new SecureRandom();

    public static String generateRandomPassword(int length) {
        if (length < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int rndCharAt = random.nextInt(PASSWORD_ALLOW.length());
            char rndChar = PASSWORD_ALLOW.charAt(rndCharAt);
            sb.append(rndChar);
        }

        return sb.toString();

    }

    // shuffle
    public static String shuffleString(String string) {
        List<String> letters = Arrays.asList(string.split(""));
        Collections.shuffle(letters);
        String result = "";
        for (String l : letters) {
            result += l;
        }
        return result;
    }
}
