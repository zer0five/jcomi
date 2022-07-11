package me.kazoku.jcomi.util;

public class ThreadUtil {

    private ThreadUtil() {
        // Utility class
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
