package com.backend;
/* 
This file functions as the primary handler/Entry point for the program, and is what the user will interface with
when inititally starting the program. 
*/


// imports
import com.libs.ScreenLib;
import com.api.Menu;
import com.libs.UserKit;
import com.libs.Consts;

public class Handle {
    private static boolean status = true;
    public static void main(String[] args) {
        // the below will be switched from running in the handle to running in the Menu.
        while (status) {
            switch (Menu.start(ScreenLib.Colors.GREEN+"Welcome to Open Messaging App "+ScreenLib.Colors.YELLOW+Consts.VERSION+ScreenLib.Colors.RESET)) {
                // The below will be updated to write to a file instead of the terminal.
                case 0: { // Menu completed successfully. Continue process.
                    System.out.println("Continue");
                    break;
                }
                case 1: { // Shutdown initiated by user. Program will exit
                    System.out.println("User-Initiated shutdown");
                    return;
                }
            }
            // this will remain
            boolean Check = UserKit.verifyAccount();
            if (Check) {
                // eventually will start networking stuff
            } else {
                throw new RuntimeException("Failed to verify authenticity of the user account. Returned "+Check);
            }
        }
    }
}
