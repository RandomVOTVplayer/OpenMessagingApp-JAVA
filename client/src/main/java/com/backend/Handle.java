package com.backend;
/* 
This file functions as the primary handler/Entry point for the program, and is what the user will interface with
when inititally starting the program. 
*/


// imports
import java.io.IOException;
import java.util.Arrays;

import com.libs.ScreenLib;
import com.api.Menu;
import com.backend.ThreadController.services;
import com.libs.Logging.Logger;
import com.libs.Consts;
import com.libs.JSONHandler;
import com.networking.ConMan;
import com.backend.RAM;


public class Handle {

    private static boolean status = true;
    public static void main(String[] args) {
        Logger.initLogger();
        RAM.Normal_Variables.ProgramArgs = args;
        Logger.log("backend/handle/main", "INFO", "Started program with the following arguments: "+Arrays.toString(RAM.Normal_Variables.ProgramArgs));
        Logger.log("backend/handler/main", "URGENT | NOTICE | LOOK AT ME", "Do not forget to remove unnecessary code and imports before V1.0.0 release!");
        try {
            for (int i = 0; i < RAM.Normal_Variables.ProgramArgs.length; i++) {
                if (RAM.Normal_Variables.ProgramArgs[i].equals("--no-Fluff") | RAM.Normal_Variables.ProgramArgs[i].equals("-nf")) {
                    RAM.Normal_Variables.noFluff = true;
                } 
                if (RAM.Normal_Variables.ProgramArgs[i].equals("-h") | RAM.Normal_Variables.ProgramArgs[i].equals("--help")) {
                    System.out.println("\nAvailable flags:\n");
                    System.out.println("-h --help          Show this page");
                    System.out.println("-nf --no-Fluff     Turns off jokes in log file");
                    return;
                }
            }
        } finally {}
        if (!(RAM.Normal_Variables.noFluff)) {
            Logger.log("backend/Handle/main", "INFO", "Note: if the Joke logs are intrusive, you can start the program with -nf or --no-Fluff to disable them.");
            ThreadController.startAThread(services.QT);
        }
        try {
            JSONHandler.ensureDataExists();
            JSONHandler.writeJsonToJava();
        } catch (IOException e) {
            Logger.log("backend/Handle/main", "FATAL", "FATAL ERROR - Problem syncing internal Data store.\n"+e);
            System.err.println(e);
            return;
        }
        ThreadController.startAThread(services.JSON_UPDATE);

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
                    ThreadController.stopAllThreads();
                    return;
                }
                default: {
                    Logger.log("backend/Handle/main", "ERROR", "The Program encountered an error and cannot continue.");
                    System.out.println(ScreenLib.Colors.RED+"The Program encountered an error and cannot continue.");
                    ThreadController.stopAllThreads();
                    return;
                }
            }
        }
        ThreadController.stopAllThreads();
    }
    
}