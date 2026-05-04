package omaj;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


class jsonData {
    class CONFIG {
        public UserData UserData;
        public TargetInfo TargetInfo;
        public Internal Internal;
    }

    class UserData {
        public String UUID;
        public String Username;
    }

    class TargetInfo {
        public String IP;
        public int Port;
        public boolean Server;
    }

    class Internal {
        public int Port;
        public String IP;
    }
}