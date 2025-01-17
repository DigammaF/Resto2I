package logic;

import java.security.SecureRandom;

public class Generation {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String WORDS = "sucre sale chaud froid amer exotique petit moyen grand glace soda plat";

    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }

    public static int generateRandomInt(int min, int max) {
        return RANDOM.nextInt(max - min) + min;
    }

    public static String generateRandomWord() {
        String[] words = WORDS.split(" ");
        int randomIndex = Generation.generateRandomInt(0, words.length - 1);
        return words[randomIndex];
    }

    public static String generateRandomWords(int count) {
        StringBuilder result = new StringBuilder();
        for (int n = 0; n < count; n++) {
            result.append(" ").append(generateRandomWord());
        }
        return result.toString();
    }
}
