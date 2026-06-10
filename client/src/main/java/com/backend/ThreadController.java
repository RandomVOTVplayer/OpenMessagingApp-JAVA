package com.backend;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import com.api.Menu;
import com.libs.JSONHandler;
import com.libs.ScreenLib;
import com.libs.Logging.Logger;

public class ThreadController {
    // updates the JSON automatically.

    private static class ThreadStatus {
        public static boolean JSONupdate = false;
        public static boolean QuadThink = false;
        public static boolean CPTP = false;
        public static boolean SM = false;
        public static boolean HEARTBEAT = false;
        public static boolean LI = false;
    }

    public enum services {
        JSON_UPDATE, // JSON update
        QT, // funni
        CPTP, // Client P2P
        SM, // Server Manager
        HEARTBEAT, // HeartBeat
        LI // Listen Incoming
    }

    private static JSONUpdate JSON_UPDATE = new JSONUpdate(); // Updates Data.json and Data.java
    private static quadThink QUAD_THINK = new quadThink(); // funni
    private static clientP2P CLIENT_PEER_TO_PEER = new clientP2P(); // Manages peer to peer connections
    private static serverMan SERVER_MANAGER = new serverMan(); // Manages server connection
    private static Heartbeat HEARTBEAT = new Heartbeat(); // heartbeat to maintain a connection
    private static ListenIncoming LISTEN_INCOMING = new ListenIncoming(); // listens for incoming connections

    public static void stopAllThreads() { // kindly stops all threads for ye
        Logger.log("backend/ThreadController", "INFO", "Stopping all threads");
        JSON_UPDATE.stopThread();
        QUAD_THINK.stopThread();
        /*
        these are not made yet :down_arrow:
        CLIENT_PEER_TO_PEER.stopThread();
        SERVER_MANAGER.stopThread();
        HEARTBEAT.stopThread();
        */
        LISTEN_INCOMING.stopThread();
    }

    public static void startAThread(Enum<services> threadName) { // starts a thread for ye
        try {
            if (threadName.equals(services.JSON_UPDATE) && !ThreadStatus.JSONupdate) {
                JSON_UPDATE.startThread();
            }
            if (threadName.equals(services.QT) && !ThreadStatus.QuadThink) {
                QUAD_THINK.startThread();
            }
            if (threadName.equals(services.CPTP) && !ThreadStatus.CPTP) {

            }
            if (threadName.equals(services.SM) && !ThreadStatus.SM) {

            }
            if (threadName.equals(services.HEARTBEAT) && !ThreadStatus.HEARTBEAT) {

            }
            if (threadName.equals(services.LI) && !ThreadStatus.LI) {
                LISTEN_INCOMING.startThread();
            }
        } catch (IOException e) {
            Logger.log("backend/ThreadController/startAThread", "FATAL", "A Thread has failed: \n"+e);
            throw new RuntimeException("A Thread has failed: \n"+e);
        }
    }

    private static class JSONUpdate implements Runnable {
        private Thread worker;
        private volatile boolean running = true;
        
        public void stopThread() {
            if (!running) return;
            Logger.log("backend/ThreadController/stopThread", "INFO", "Thread JSONUpdate stopping");
            running = false;
            if (worker != null) {
                worker.interrupt();
                try { worker.join(2000); } catch (InterruptedException ignored) {}
            }
            ThreadStatus.JSONupdate = false;
        }

        public void startThread() {
            Logger.log("backend/ThreadController/startThread", "INFO", "Thread JSONUpdate starting");
            worker = new Thread(this, "JSONUpdate");
            worker.start();
            ThreadStatus.JSONupdate = true;
        }

        public void run() {
            Logger.log("backend/ThreadController/JSONUpdate/run", "INFO", "Thread JSONUpdate is Running");
            while (running) {
                ScreenLib.pause(5000);
                if(JSONHandler.writeJavaToJSON() == 1) {
                    Logger.log("backend/ThreadController/run", "WARN", "Thread 'worker' under ThreadController has stopped. Data will no longer be synchronized.");
                    System.err.println("Thread 'worker' under ThreadController has stopped. Data will no longer be synchronized.");
                    running = false;
                }
            }
            Logger.log("backend/ThreadController/JSONUpdate/run", "INFO", "Thread JSONUpdate has stopped");
            return;
        }
    }

    private static class quadThink implements Runnable {
        private volatile boolean running = false;
        private Thread worker;

        public synchronized void startThread() {
            if (running) return;
            running = true;
            worker = new Thread(this, "quadThink");
            worker.start();
            ThreadStatus.QuadThink = true;
        }
        public synchronized void stopThread() {
            running = false;
            if (worker != null) {
                Logger.log("backend/ThreadController/quadThink/stopThread", "INFO", "Intterrupting Thread.");
                worker.interrupt();
                try { worker.join(2000); } catch (InterruptedException ignored) {}
            }
            /*
            if (worker == null) {
                Logger.log("debug", "debug", "the thing returned null");
            }
            */
            ThreadStatus.QuadThink = false;
        }
        @Override
        public void run() {
            while (running) {
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    Logger.log("backed/ThreadController/quadThink", "ERROR", "Encountered excepion\n"+e);
                    return;
                }
                Logger.log("backend/ThreadController/quadThink", "JOKE", "https://www.youtube.com/watch?v=VOXYMRcWbF8&list=RDVOXYMRcWbF8&start_radio=1");
            }
        }
    }

    private static class clientP2P {
        // Methods for managing P2P connections

    }
    private static class serverMan {} //manages specific information for use with server connections

    private static class Heartbeat /*implements Runnable*/ {
        // buh dum
    }

    // Thread
    private static class ListenIncoming implements Runnable {
        // listens for incoming connections
        private volatile boolean running = false;
        private ServerSocket server;
        private Thread worker;


        public synchronized void startThread() throws IOException {
            if (running) {
                return;
            }
            Logger.log("networking/ConMan/ListenIncoming/startThread", "INFO", "Starting thread ListenIncoming");
            running = true;

            server = new ServerSocket();
            server.setReuseAddress(true);
            server.bind(new InetSocketAddress(Data.STORE.internal.ip, Data.STORE.internal.port));

            // actually starts the thread
            worker = Thread.ofVirtual().unstarted(this);
            worker.start();
            ThreadStatus.LI = true;
            ScreenLib.Clear();
            System.out.println("Now listening for connection requests");
            ScreenLib.pause(750);
        }
        
        public synchronized void stopThread() {
            Logger.log("networking/ConMan/ListenIncoming/stopThread", "INFO", "Stopping Thread ListenIncoming");
            running = false;
            try {
                if (server != null && !server.isClosed()) server.close();
            } catch (IOException ignored) {}
            if (worker != null) {
                try { worker.join(2000); } catch (InterruptedException ignored) {}
            }
            ThreadStatus.LI = false;
        }

        @Override
        public void run() {
            Logger.log("networking/ConMan/ListenIncoming/run", "INFO", "Thread ListenIncoming Running");
            try (ServerSocket s = server) { // using the already bound server
                while (running) {
                    try {
                        Socket client = s.accept();
                        Logger.log("networking/ConMan/ListenIncoming/run", "INFO", "Incoming request");
                        Thread.ofVirtual().start(() -> Menu.chat(client));
                    } catch (SocketException e) {
                        if (!running) break; // expected during shutdown
                    } catch (IOException e) {
                        Logger.log("networking/ConMan/ListenIncoming/run", "ERROR", ("error caught\n"+e));
                    }
                }
            } catch (Exception e) {
                Logger.log("networking/ConMan/ListenIncoming/run", "ERROR", ("Fatal error\n"+e));
                return;
            }
            Logger.log("networking/ConMan/ListenIncoming/run", "INFO", "Thread ListenIncoming Stopped");
            return;
        }

        private void handleClient(Socket client) { //repurpose to facciliate the Chat
            Logger.log("networking/ConMan/ListenIncoming/handleClient", "INFO", "Recieved Socket information");
            try (Socket c = client;
                InputStream in = c.getInputStream();
                OutputStream out = c.getOutputStream()) {
                System.out.printf("InputStream: %s OutputStream: %s remote: %s local: %s%n", in, out, c.getRemoteSocketAddress(), c.getLocalSocketAddress());
            } catch (IOException e) {
                Logger.log("handleClient", "ERROR", e.toString());
            }
        }
    }
}