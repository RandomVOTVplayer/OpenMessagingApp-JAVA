package com.backend;
/* 
This file functions as the primary handler/Entry point for the program, and is what the user will interface with
when inititally starting the program. 
*/


// imports
import java.io.IOException;

import com.libs.ScreenLib;
import com.api.Menu;
import com.libs.Logging.Logger;
import com.libs.Consts;
import com.libs.JSONHandler;
import com.networking.ConMan;


public class Handle {
    private static boolean status = true;
    public static void main(String[] args) {
        Logger.initLogger();
        try {
            JSONHandler.ensureDataExists();
            JSONHandler.writeJsonToJava();
        } catch (IOException e) {
            System.err.println(e);
            return;
        }
        // JSON threading
        ThreadController.JSONUpdate JSONThread = new ThreadController.JSONUpdate();
        JSONThread.startThread();

        // the below will be switched from running in the handle to running in the Menu.
        while (status) {
            switch (Menu.start(ScreenLib.Colors.GREEN+"Welcome to Open Messaging App "+ScreenLib.Colors.YELLOW+Consts.VERSION+ScreenLib.Colors.RESET)) {
                // The below will be updated to write to a file instead of the terminal.
                case 0: { // Menu completed successfully. Continue process.
                    Logger.log("Handle", "INFO", "Continuing to Networking connection.");
                    break;
                }
                case 1: { // Shutdown initiated by user. Program will exit
                    Logger.log("Handle", "INFO", "User-Initiated shutdown.");
                    JSONThread.stopThread();
                    return;
                }
                default: {
                    Logger.log("backend/Handle/main", "ERROR", "The Program encountered an error and cannot continue.");
                    System.out.println(ScreenLib.Colors.RED+"The Program encountered an error and cannot continue.");
                    JSONThread.stopThread();
                    return;
                }
            }
            // this will remain
            boolean Check = true; //UserKit.verifyAccount();
            if (Check) {
                ConMan cm = new ConMan();
                cm.PASC();
            } else {
                JSONThread.stopThread();
                Logger.log("Handle", "ERROR", "Failed to verify authenticity of the user account. Returned "+Check);
                throw new RuntimeException("Failed to verify authenticity of the user account. Returned "+Check);
            }
        }
        JSONThread.stopThread();
    }
    
}