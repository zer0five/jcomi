package org.jcomi.util;

public class ThreadUtil {

    private ThreadUtil() {
        // Utility class
    }

    public static void sleepSafe(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
