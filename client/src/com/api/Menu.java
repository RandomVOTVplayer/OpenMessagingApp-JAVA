package com.api;
/* This file acts as the storage for all Menus that will be used. Each menu will be split by comments. */

import com.libs.ScreenLib;
import com.libs.UserKit;
import com.libs.ScreenLib.Colors;
import com.backend.Data;
import com.libs.IO;
import java.net.InetAddress;

public class Menu {
    static String red = ScreenLib.Colors.RED;
    static String green = ScreenLib.Colors.GREEN;
    static String yellow = ScreenLib.Colors.YELLOW;
    static String reset = ScreenLib.Colors.RESET;
    // Main Menu
    public static int start(String welcome) {
        String[] options = {"Connect","Settings","About the program","Quit"};
        boolean reprint = true;

        while (reprint) {
            ScreenLib.Clear();
            System.out.println(welcome);
            for(int i = 0; i < options.length; i++) {
                System.out.println((i+1)+". "+options[i]);
            };
            IO.write("Use the numbers above to make a selection: ");
            switch (Integer.parseInt(IO.read("return"))) {
                case 1: {
                    reprint = false; // is this neccesary?
                    return 0;
                }
                case 2: {
                    settings();
                    break;
                }
                case 3: {
                    About.Tutorial.TutorialStart();
                    break;
                }
                case 4: {
                    reprint = false;
                    return 1;
                }
                default: {
                    throw new RuntimeException("Invalid response - Cannot pick options below 1 or greater than 4.");
                }
            }
        }
        return 500;
    }
    // Account Creation
    public static void createAccount() {
        //works on account creation. This should usually only be needed once.
        ScreenLib.Clear();
        System.out.println(red+"--------WARNING/DISCLAIMER--------"+yellow);
        System.out.println("Accounts are lists of specific information stored locally on a device, and requested by the Server/Client you are connecting to.\nThis information and a few others may be shared with servers(group chats) or clients (p2p).\nInformation that may be shared is: IP address, UUID, Username.");
        System.out.println("This information is required to ensure that all functionality is preserved, and is not shared outside of who you are connected to.");
        System.out.println("For more information about information used and stored, please go to [LINK]");
        IO.write("Please press 1 and enter to continue "+reset);
        ScreenLib.Clear();
        System.out.println(green+"Welcome to account creation!"+reset+"\nNote that only one account can be used per device.\nTo Create an account, please answer the following questions:");
        // in the future, make this part more compact/automated.
        IO.write("What do you want to call yourself? ");
        Data.USER_DATA.Username = IO.read("return");
        ScreenLib.Clear();
        System.out.println("Hello "+Data.USER_DATA.Username+"! Answer the following questions:");
        IO.write("What IP should this client listen too? (use 'def' for default) ");
        switch (IO.read("return")) {
            case "def": {
                Data.INTERNAL.IP =  "0.0.0.0";
                break;
            }
            default: {
                Data.INTERNAL.IP = IO.read("return");
                break;
            }
        }
        Data.INTERNAL.Port = 1773;
        ScreenLib.Clear();
        System.out.println("Account setup complete! Returning to main menu...");
        ScreenLib.pause(1000);
        return;
    }

    public static void settings() {
        //making sure an account is actually registered
        if (UserKit.verifyAccount() != true) {
            throw new RuntimeException("Account verification failed");
        }
        boolean go = true;
        while (go) {
            ScreenLib.Clear();
            System.out.println(Colors.GREEN+"Settings:"+Colors.RESET);
            System.out.println("Choose a number below");
            // yes, i know this is Highly inefficient and a waste of computation time but I will improve this in the future.
            System.out.println("0. Quit\n1. User settings\n2. Privacy\n3. Program Config");
            IO.write("");
            switch (Integer.parseInt(IO.read("return"))) {
                case 0: {
                    go = false;
                    break;
                }
                case 1: {
                    ScreenLib.Clear();
                    System.out.println("0. Go Back\n1. Change Username");
                    IO.write("");
                    switch (Integer.parseInt(IO.read("return"))) {
                        case 0: {
                            break;
                        }
                        case 1: {
                            ScreenLib.Clear();
                            System.out.println("Your current username is "+Data.USER_DATA.Username);
                            IO.write("What do you want your username to be? ");
                            Data.USER_DATA.Username = IO.read("return");
                            System.out.println("Your username is now "+Data.USER_DATA.Username);
                            ScreenLib.pause(1500);
                            break;
                        }
                    }
                    break;
                }
                case 2: {
                    ScreenLib.Clear();
                    System.out.println("Nothing to keep private right now. There isnt even the option to connect to another device!");
                    ScreenLib.pause(2500);
                    break;
                }
                case 3: {
                    ScreenLib.Clear();
                    System.out.println(Colors.GREEN+"Settings - Program Config"+Colors.RESET);
                    System.out.println("0. Go back\n1. Change listening IP\n2. Change listening port");
                    IO.write("");
                    switch (Integer.parseInt(IO.read("return"))) {
                        case 0: {
                            break;
                        }
                        case 1: {
                            ScreenLib.Clear();
                            System.out.println(Colors.GREEN+"Settings - Program Config - Change Listening IP"+Colors.RESET);
                            System.out.println("Your current IP is "+Data.INTERNAL.IP);
                            IO.write("What do you want the listening IP to be? Note: Must be IPv4 address. Default is 0.0.0.0 ");
                            Data.INTERNAL.IP = IO.read("return");
                            System.out.println("Your listening IP is now "+Data.INTERNAL.IP);
                            ScreenLib.pause(1500);
                            break;
                        }
                        case 2: {
                            ScreenLib.Clear();
                            System.out.println(Colors.GREEN+"Settings - Program Config - Change Listening Port"+Colors.RESET);
                            System.out.println(Colors.RED+"WARNING"+Colors.YELLOW+" Changing the listening port will not gaurentee that connections will work."+Colors.RESET);
                            System.out.println("Your current port is "+Data.INTERNAL.Port);
                            IO.write("What do you want the port to be? Default in 1773: ");
                            Data.INTERNAL.Port = Integer.parseInt(IO.read("return"));
                            System.out.println("Your port is now "+Data.INTERNAL.Port);
                            ScreenLib.pause(1500);
                            break;
                        }
                    }
                }
            }
            
        }
    }
}