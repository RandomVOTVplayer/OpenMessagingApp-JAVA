package com.api;
/* This file acts as the storage for all Menus that will be used. Each menu will be split by comments. */

import com.libs.ScreenLib;
import com.libs.UserKit;
import com.networking.ConMan;
import com.libs.Logging.Logger;
import com.libs.ScreenLib.Colors;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

import com.backend.Data;
import com.libs.IO;;

public class Menu {
    static String red = ScreenLib.Colors.RED;
    static String green = ScreenLib.Colors.GREEN;
    static String yellow = ScreenLib.Colors.YELLOW;
    static String reset = ScreenLib.Colors.RESET;
    public static boolean interrupt = false;
    // Main Menu
    public static int start(String welcome) {
        Logger.log("api/Main/start", "INFO", "Initializing 'Menu.java'");
        String[] options = {"Connect","Settings","About the program","Quit"};
        boolean reprint = true;

        while(true) {
            try {
                Thread.sleep(750); 
            } catch (InterruptedException e) {
                Logger.log("api/Menu/start", "FATAL", "Runtime Exception caught\n"+e);
                throw new RuntimeException(e);
            }
                while (reprint && !(interrupt)) {
                ScreenLib.Clear();
                System.out.println(welcome);
                for(int i = 0; i < options.length; i++) {
                    System.out.println((i+1)+". "+options[i]);
                };
                IO.write("Use the numbers above to make a selection: ");
                switch (Integer.parseInt(IO.read("return"))) {
                    case 1: {
                        ConMan cm = new ConMan();
                        ConMenu();
                        break;
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
                        System.out.println("Invalid Response. Exiting...");
                        return 2;
                    }
                }
            }
            return 500;
        }
    }
    // Account Creation
    public static void createAccount() {
        Logger.log("Menu|createAccount", "INFO", "Initializing account creation");
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
        Data.STORE.userData.username = IO.read("return");
        ScreenLib.Clear();
        System.out.println("Hello "+Data.STORE.userData.username+"! Answer the following questions:");
        IO.write("What IP should this client listen too? (use 'def' for default) ");
        switch (IO.read("return")) {
            case "def": {
                Data.STORE.internal.ip = "0.0.0.0";
                break;
            }
            default: {
                Data.STORE.internal.ip = IO.read("return");
                break;
            }
        }
        ScreenLib.Clear();
        System.out.println("Account setup complete! Returning to main menu...");
        ScreenLib.pause(1000);
        return;
    }

    // Settings screen
    public static void settings() {
        Logger.log("Menu|settings", "INFO", "Initializing Settings.");
        //making sure an account is actually registered
        if (UserKit.verifyAccount() != true) {
            throw new RuntimeException("Account verification failed");
        }
        boolean go = true;
        while (go && !(interrupt)) {
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
                            System.out.println("Your current username is "+Data.STORE.userData.username);
                            IO.write("What do you want your username to be? ");
                            Logger.log("Menu|settings", "INFO", ("Username changed. Was "+Data.STORE.userData.username+". Now "+IO.read("return")));
                            Data.STORE.userData.username = IO.read("return");
                            System.out.println("Your username is now "+Data.STORE.userData.username);
                            ScreenLib.pause(1500);
                            break;
                        }
                    }
                    break;
                }
                case 2: {
                    ScreenLib.Clear();
                    System.out.println("Nothing to keep private right now. There isnt even the option to connect to another device!");
                    Logger.log("Menu|settings", "INFO", "Little did the user know, this program is so incomplete that there are no settings to change privacy.");
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
                            System.out.println("Your current IP is "+Data.STORE.internal.ip);
                            IO.write("What do you want the listening IP to be? Note: Must be IPv4 address. Default is 0.0.0.0 ");
                            Logger.log("Menu|settings", "INFO", ("IP Changed. Was "+Data.STORE.internal.ip+". Is now "+IO.read("return")));
                            Data.STORE.internal.ip = IO.read("return");
                            System.out.println("Your listening IP is now "+Data.STORE.internal.ip);
                            ScreenLib.pause(1500);
                            break;
                        }
                        case 2: {
                            ScreenLib.Clear();
                            System.out.println(Colors.GREEN+"Settings - Program Config - Change Listening Port"+Colors.RESET);
                            System.out.println(Colors.RED+"WARNING"+Colors.YELLOW+" Changing the listening port will not gaurentee that connections will work."+Colors.RESET);
                            System.out.println("Your current port is "+Data.STORE.internal.port);
                            IO.write("What do you want the port to be? Default in 1773: ");
                            Data.STORE.internal.port = Integer.parseInt(IO.read("return"));
                            System.out.println("Your port is now "+Data.STORE.internal.port);
                            Logger.log("Menu|settings", "INFO", ("Listening port changed. Was "+Data.STORE.internal.port+". Is now "+IO.read("return")));
                            Logger.log("Menu|settings", "WARN", "Listening port changed. This may cause issues with the program.");
                            ScreenLib.pause(1500);
                            break;
                        }
                    }
                }
            }
            
        }
    }

    // Communication Menu
    public static void chat(Socket request) {
        Logger.log("api/Menu/chat", "INFO", "Interupt flag changed to true");
        interrupt = true;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {} //if program starts having problems, inspect this
        ScreenLib.Clear();
        UserKit.verifyAccount();
        System.out.println("You have a new connection request from the following IP!\n");
        Logger.log("networking/ConMan/ListenIncoming/handleClient", "INFO", "Recieved Socket information");
        try (Socket c = request;
            InputStream in = c.getInputStream();
            OutputStream out = c.getOutputStream()) {
            System.out.printf("InputStream: %s OutputStream: %s remote: %s local: %s%n", in, out, c.getRemoteSocketAddress(), c.getLocalSocketAddress());
        } catch (IOException e) {
            Logger.log("handleClient", "ERROR", e.toString());
        }
        IO.write("Do you wish to accept the connection? (Y/N): ");
        Scanner scan = new Scanner(System.in);
        String response = scan.nextLine();
        scan.close();
        if (response.toUpperCase().equals("N")) {
            interrupt = false;
            return;
        }
    }
    private static void ConMenu() {
        Logger.log("api/Menu/ConMenu", "INFO", "preparing Connection Menu");
        String[] options = {"Start/Stop listening for connection requests.", "Attempt a connection"};
        ScreenLib.Clear();
        System.out.println(green+"Connection Menu"+reset);
        for (int i = 0; i < options.length; i++) {
            System.out.println((i+1)+". "+options[i]);
        }
        IO.write("Make a selection from the numbers above: ");
    }
}