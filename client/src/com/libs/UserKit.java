package com.libs;

import com.api.Menu;
import com.backend.Data;

public class UserKit {
    public static class Generation { // Focuses on Generating values such as a UUID or Encryption key.
        public static void GenUUID() {
            // add generation code here. for now, sets the UUID to "AppleBottomJeans"
            Data.USER_DATA.UUID = "AppleBottomJeans";
        }
    }

    public static class Verification { // Focuses on verifying values with specialized methods.
        public static boolean IDCheck() {
            if (Data.USER_DATA.UUID != null) {
                return true; // UUID exists
            } else {
                return false; // UUID is null
            }
        }
    }
    public static boolean verifyAccount() { // make sure that a user account is registered.
        if (true) { // remove this at some point
            if (UserKit.Verification.IDCheck()) {
                ScreenLib.Clear();
                System.out.println("Account verification complete! Your UUID is "+Data.USER_DATA.UUID+", With username "+Data.USER_DATA.Username);
                System.out.println("Press '1' and 'enter' to continue");
                IO.write("");
            } else {
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
