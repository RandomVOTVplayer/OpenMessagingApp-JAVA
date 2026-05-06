package com.libs;

// just tried to do linux sudo lol

/*
How to use:
----CLEARING THE SCREEN----
After importing com.libs.ScreenLib,
use ScreenLib.Clear() to clear the screen.

----PAUSE----
This is the "Busy-wait" pausing method. To use, import the library (step is above)
and then use ScreenLib.pause([TIME IN MILLISECONDS]) to pause for the specified amount of time.
Note that 1000ms == 1 second.
*/

public class ScreenLib {
    // This class stored functions for Screen control
    public static void Clear() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.out.println("Error caught in ScreenLib.java");
            e.printStackTrace();
        }
    }
    
    public static void pause(time) {
        try {
            Thread.sleep(time)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // restore interrupt status
        }
    }
}