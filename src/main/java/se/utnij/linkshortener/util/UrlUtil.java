package se.utnij.linkshortener.util;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UrlUtil {

    private final static int MIN_PATH_LENGTH = 3;
    private final static int MAX_PATH_LENGTH = 5;
    private final static String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private final static Random random = new Random();

    public static String generateRandomPath() {
        int pathLength = random.nextInt(MAX_PATH_LENGTH - MIN_PATH_LENGTH + 1) + MIN_PATH_LENGTH;
        return IntStream.range(0, pathLength)
                .mapToObj(i -> CHARACTERS.charAt(random.nextInt(CHARACTERS.length())))
                .map(String::valueOf)
                .collect(Collectors.joining());
    }
}