package com.api;


/*
    Program name: About The Program (ATP)
    Description: This program is a manual for how to navigate the application.
*/

import com.libs.ScreenLib;
import com.libs.Logging.Logger;
import com.libs.IO;

public class About {
    public static class Tutorial {
        static String[] sites = { // these are the different pages that will be shown
            "HOW TO NAVIGATE:\nUse numbers to navigate pages. Press 0 to return to quit and return to the program.\nEnter '1' to see the table of contents\n\nYOU MUST PRESS ENTER TO MAKE A SELECTION",
            "TABLE OF CONTENTS\n0 - Quit\n1 - Table of Contents"
        };
        static int page = 0; // current page index
        static boolean debug = false; //toggles some debug outputs
        public static void TutorialStart() {
            Logger.log("About|Tutorial", "INFO", "Initializing Tutorial");
            while (true) {
                ScreenLib.Clear();
                if (page >= sites.length | page < 0) {
                    System.out.println(ScreenLib.Colors.RED+"END OF THE LINE"+ScreenLib.Colors.RESET+"\nSo, you cannot pick values greater than or equal to "+sites.length+" or less than 0.\n\nAlso this program doesnt really have documentation built in yet.");
                } else {
                    System.out.println(sites[page]);
                }
                IO.write("What page would you like to view next? (press 1 for the Table of Contents) ");
                page = Integer.parseInt(IO.read("return"));
                if (page == 0) {
                    break;
                }
            }
        }
    }
}
