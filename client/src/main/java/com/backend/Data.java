package com.backend;

public class Data {
    public static Boolean threadStatus = true; //purely so multiple threads can start/stop at the same time.
    // Public static variables for global access to configuration and data.
    //public static final Config CONFIG = new Config();
    public static class DataStore {
        public final UserData userData = new UserData();
        public final TargetInfo targetInfo = new TargetInfo();
        public final Internal internal = new Internal();
    }
    public static final DataStore STORE = new DataStore();

    // Remove the commented code below before release.
    /*public static class Config { //Program configuration.
        // Consider removal of this class.
        public UserData UserData;
        public TargetInfo TargetInfo; //
        public Internal Internal; //forgot. Add Comment Later for what it is used for
    }*/

    public static class UserData { //Client-Side data for the user.
        public String uuid = "none"; // Unique User Identification. Allows for persistant storage, and is generated once.
        public String username = "none"; // User-Chosen username
    }

    public static class TargetInfo { //Target information for the connection.
        public String ip = "none"; // Target IP
        public int port = 0; // Target port
        public boolean server = false; // If the client will connect to a server
    }

    public static class Internal { // Internal data of program configuation. Used for internal use only.
        public int port = 1773; // Listening port & Boston Tea Party referance
        public String ip = "0.0.0.0"; // Listening IP
    }

    public static class RuntimeInformationRepository {
        // Typically information received from a server.
        public static int clientCount = 0;
        public static String[] connectedUsers;
    }
}