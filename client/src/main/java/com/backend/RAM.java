package com.backend;

public class RAM {
    /*
    As it sounds, RAM in this program is intended to be volatile
    variables. Or normal variables. Either works, as long as it
    needs to be accessed by different parts.
    
    I also did not really want to store these in Data.java*/
    

    public static class Volatile_Variables { // Volatile Variables
        public static volatile Boolean threadStatus = true; //purely so multiple threads can start/stop at the same time.
    }

    public static class Normal_Variables { // Normal Variables
        public static String[] ProgramArgs; // Program Arguments
        public static boolean noFluff = false; // No joke code
    }
}
