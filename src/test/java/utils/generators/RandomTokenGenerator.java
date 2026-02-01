package utils.generators;

import java.util.Random;

public class RandomTokenGenerator {

    private static final String VALID_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final Random RANDOM = new Random();

    public static String generateValidToken() {
        StringBuilder sb = new StringBuilder(32);
        for (int i = 0; i < 32; i++) {
            sb.append(VALID_CHARS.charAt(RANDOM.nextInt(VALID_CHARS.length())));
        }
        return sb.toString();
    }

    public static String generateInvalidToken(int length) {
        StringBuilder sb = new StringBuilder(length);
        String allChars = VALID_CHARS + "abcdefghijklmnopqrstuvwxyz!@#$%^&*()";
        for (int i = 0; i < length; i++) {
            sb.append(allChars.charAt(RANDOM.nextInt(allChars.length())));
        }
        return sb.toString();
    }
}
