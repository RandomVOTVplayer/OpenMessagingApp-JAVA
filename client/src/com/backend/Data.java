package com.backend;

public class Data {
    // Public static variables for global access to configuration and data.
    //public static final Config CONFIG = new Config();
    public static final UserData USER_DATA = new UserData();
    public static final TargetInfo TARGET_INFO = new TargetInfo();
    public static final Internal INTERNAL = new Internal();

    /*public static class Config { //Program configuration.
        // Consider removal of this class.
        public UserData UserData;
        public TargetInfo TargetInfo; //
        public Internal Internal; //forgot. Add Comment Later for what it is used for
    }*/

    public static class UserData { //Client-Side data for the user.
        public String UUID; // Unique User Identification. Allows for persistant storage, and is generated once.
        public String Username; // User-Chosen username
    }

    public static class TargetInfo { //Target information for the connection.
        public String IP; // Target IP
        public int Port; // Target port
        public boolean Server; // If the client will connect to a server
    }

    public static class Internal { // Internal data of program configuation. Used for internal use only.
        public int Port = 1773; // Listening port
        public String IP = "0.0.0.0"; // Listening IP
    }
}