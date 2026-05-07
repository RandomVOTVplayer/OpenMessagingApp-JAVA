package com.main;

public class Data {
    // Public static variables for global access to configuration and data.
    public static final Config CONFIG = new Config();
    public static final UserData USER_DATA = new UserData();
    public static final TargetInfo TARGET_INFO = new TargetInfo();
    public static final Internal INTERNAL = new Internal();

    public static class Config { //Program configuration.
        // Consider removal of this class.
        public UserData UserData;
        public TargetInfo TargetInfo; //
        public Internal Internal; //forgot. Add Comment Later for what it is used for
    }

    public static class UserData { //Client-Side data for the user.
        public String UUID;
        public String Username;
    }

    public static class TargetInfo { //Target information for the connection.
        public String IP;
        public int Port;
        public boolean Server;
    }

    public static class Internal { // Internal data of program configuation. Used for internal use only.
        public int Port;
        public String IP;
    }
    public static void runTest() {
        System.out.println(Data.USER_DATA.UUID+"[OLD]");
        Data.USER_DATA.UUID = "cba321";
    }
}