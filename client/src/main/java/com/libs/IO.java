package com.libs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import com.api.Menu;
import com.libs.Logging.Logger;

public class IO {
    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    private static int jokeCounter = 0;
    private static class Store {
        static volatile String input;
    }

    public static void write(String ask) {
        System.out.print(ask);
        Store.input = readLineInterruptible();
    }

    public static String read(String type) {
        if ("out".equals(type)) {
            if (Store.input != null) {
                System.out.println(Store.input);
            } else {
                Logger.log("libs/IO/read", "FATAL", "Store.input returned null");
                throw new RuntimeException("Store.input returned null");
            }
            return "";
        } else if ("return".equals(type)) {
            if (Store.input != null) {
                return Store.input;
            } else {
                Logger.log("libs/IO/read", "FATAL", "Store.input returned null");
                throw new RuntimeException("Store.input returned null");
            }
        }
        return "Make sure that 'out' and 'return' are lowercase - IO.java: Line 32";
    }

    private static String readLineInterruptible() {
        try {
            while (true) {
                // If Menu.interrupt is set, abort and return null
                if (Menu.interrupt) {
                    Logger.log("libs/IO/readLineInterruptible", "INFO", "Interrupted reading of the line");
                    return null;
                }
                if (in.ready()) {
                    String line = in.readLine();
                    return line;
                }
                try {
                    jokeCounter = jokeCounter + 1; // fun fact: it takes about 25 seconds for this counter to reach 500
                    if (jokeCounter >= 500) {
                        Logger.log("libs/IO", "JOKE", "sleepy time :)");
                        jokeCounter = 0;
                    }
                        Thread.sleep(50); // small sleep to avoid busy wait
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    return null;
                }
            }
        } catch (IOException e) {
            Logger.log("libs/IO/readLineInterruptible", "FATAL", "IO error reading input\n"+e);
            throw new RuntimeException("I/O error reading input", e);
        }
    }
}
