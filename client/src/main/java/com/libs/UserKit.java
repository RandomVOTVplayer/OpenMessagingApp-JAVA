package com.libs;

import com.api.Menu;
import com.backend.Data;
import com.libs.Logging.Logger;

public class UserKit {
    public static class Generation { // Focuses on Generating values such as a UUID or Encryption key.
        public static void GenUUID() {
            // add generation code here. for now, sets the UUID to "AppleBottomJeans"
            Data.STORE.userData.uuid = "AppleBottomJeans";
            Logger.log("libs/UserKit/Generation/GenUUID", "INFO", ("UUID created: "+Data.STORE.userData.uuid));
        }
    }

    public static class Verification { // Focuses on verifying values with specialized methods.
        public static boolean IDCheck() {
            if (!"none".equals(Data.STORE.userData.uuid)) {
                Logger.log("libs/UserKit/Verification/IDCheck", "INFO", "UUID exists");
                return true; // UUID exists
            } else {
                Logger.log("libs/UserKit/Verification/IDCheck", "INFO", "UUID does not exist");
                return false; // UUID is null
            }
        }
    }
    public static boolean verifyAccount() { // make sure that a user account is registered.
        Logger.log("libs/UserKit/verifyAccount", "INFO", "Verifying user account.");
        if (true) { // remove this at some point
            if (Verification.IDCheck()) {
                Logger.log("libs/UserKit/verifyAccount", "INFO", "Account confirmed. proceeding.");
                ScreenLib.Clear();
                System.out.println("Account verification complete! Your UUID is "+Data.STORE.userData.uuid+", With username "+Data.STORE.userData.username);
                System.out.println("Press '1' and 'enter' to continue");
                IO.write("");
            } else {
                Logger.log("libs/UserKit/verifyAccount", "INFO", "User account could not be determined. Likely missing UUID. Creating new UUID.");
                UserKit.Generation.GenUUID();
                int number = 5;
                for (int i=1; i < 6; i++) {
                    ScreenLib.Clear();
                    System.out.println("It appears you have no stored account on this device. Loading account creation\n"+number);
                    number = 5 - i;
                    ScreenLib.pause(1000);
                }
                Menu.createAccount();
            }
        }
        return true;
    }
}
