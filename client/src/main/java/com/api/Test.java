package com.api;

import java.io.IOException;

import com.backend.ThreadController;
//This file should be removed for releases
//file is only supposed to test functions and setups. In other words, Debug.

public class Test {
    public static void main(String[] args) {
        ThreadController.startAThread(ThreadController.services.JSON_UPDATE);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignored) {};
        ThreadController.stopAllThreads();
    }
}