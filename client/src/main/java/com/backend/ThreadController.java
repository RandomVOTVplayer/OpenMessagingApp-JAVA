package com.backend;

import com.libs.JSONHandler;
import com.libs.ScreenLib;
import com.libs.Logging.Logger;

public class ThreadController {
    public static class JSONUpdate implements Runnable {
        private Thread worker;
        private volatile boolean running = true;
        
        public void stopThread() {
            Logger.log("backend/ThreadController/stopThread", "INFO", "Thread JSONUpdate stopping");
            running = false;
        }

        public void startThread() {
            Logger.log("backend/ThreadController/startThread", "INFO", "Thread JSONUpdate starting");
            worker = new Thread(this);
            worker.start();
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
}