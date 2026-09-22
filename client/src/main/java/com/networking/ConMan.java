package com.networking;
import com.backend.Data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

import com.libs.Logging.Logger;
import com.libs.ScreenLib;
import com.libs.UserKit;
import com.libs.IO;
import com.api.Menu;
import com.backend.ThreadController;
import com.backend.ThreadController.services;

public class ConMan {

    public static void PASC() { // Prepare And Start Connection. Or PASCO (the O being the '()')
        //This is to be called prior to every connection attempt.
        if (UserKit.verifyAccount() == false) {
            Logger.log("networking/ConMan/openSocket", "ERROR", "Could not verify account.");
            return;
        }
        Logger.log("ConMan/PASC", "INFO", "Preparing Connection Manager");
        ScreenLib.Clear();
        if ("none".equals(Data.STORE.targetInfo.ip)) {
            Logger.log("ConMan/PASC", "INFO", "No pre-existing connection detected. Preparing new connection.");
            Logger.log("ConMan/PASC", "INFO", ("Current targetInfo:\n"+"IP: "+Data.STORE.targetInfo.ip+"\nPort: "+Data.STORE.targetInfo.port+"\nIf the target is a Server: "+Data.STORE.targetInfo.server));
            System.out.println("It appears as there is no past connection. Please enter the following details:");
            IO.write("Please put in the IP of the target you wish to connect to (I.e 127.0.0.1): ");
            Data.STORE.targetInfo.ip = IO.read("return");
            Logger.log("networking/ConMan/PASC", "INFO", ("Set target IP to "+Data.STORE.targetInfo.ip));
            IO.write("Please enter the port of the client you wish to connect to (Press enter to set to default): ");
            if (!IO.read("return").equals(null)) {
                if (!IO.read("return").equals("")) {
                    Data.STORE.targetInfo.port = 1773;
                } else {
                    Data.STORE.targetInfo.port = Integer.parseInt(IO.read("return"));
                }
                Logger.log("networking/ConMan/PASC", "INFO", ("Set target port as "+IO.read("return")));
            } else {
                Logger.log("networking/ConMan/PASC", "INFO", "Set target port as 1773");
                Data.STORE.targetInfo.port = 1773;
            }
            Logger.log("networking/ConMan/PASC", "INFO", ("New targetInfo:\n"+"IP: "+Data.STORE.targetInfo.ip+"\nPort: "+Data.STORE.targetInfo.port+"\nIf the target is a Server: "+Data.STORE.targetInfo.server));
        } else {
            Logger.log("networking/ConMan/PASC", "INFO", "Previous target found. Prompting user.");
            System.out.println("It appears that there are old connection information. Below are the following information:");
            System.out.println("IP: "+Data.STORE.targetInfo.ip+"\nPort: "+Data.STORE.targetInfo.port);
            IO.write("Do you wish to make the connection? (Y/N)");
            if (!"Y".equals(IO.read("return").toUpperCase())) {
                ScreenLib.Clear();
                Logger.log("ConMan/PASC", "INFO", "User-Requested reassignment of Target Info");
                Logger.log("ConMan/PASC", "INFO", ("Current targetInfo:\n"+"IP: "+Data.STORE.targetInfo.ip+"\nPort: "+Data.STORE.targetInfo.port+"\nIf the target is a Server: "+Data.STORE.targetInfo.server));
                System.out.println("Please enter the following details:");
                IO.write("Please put in the IP of the target you wish to connect to (I.e 127.0.0.1): ");
                Data.STORE.targetInfo.ip = IO.read("return");
                Logger.log("networking/ConMan/PASC", "INFO", ("Set target IP to "+Data.STORE.targetInfo.ip));
                IO.write("Please enter the port of the client you wish to connect to (Press enter to use default): ");
                if (!IO.read("return").equals("")) {
                    Data.STORE.targetInfo.port = Integer.parseInt(IO.read("return"));
                    Logger.log("networking/ConMan/PASC", "INFO", ("Set target port as "+IO.read("return")));
                } else {
                    Logger.log("networking/ConMan/PASC", "INFO", "Set target port as 1773");
                    Data.STORE.targetInfo.port = 1773;
                }
                Logger.log("networking/ConMan/PASC", "INFO", ("New targetInfo:\n"+"IP: "+Data.STORE.targetInfo.ip+"\nPort: "+Data.STORE.targetInfo.port+"\nIf the target is a Server: "+Data.STORE.targetInfo.server));
            }
        }
        Logger.log("networking/ConMan/PASC", "INFO", "Start networked connection.");
        ThreadController.startAThread(services.LI);
    }
}
