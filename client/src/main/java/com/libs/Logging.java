package com.libs;

import java.io.FileWriter;
import java.io.IOException;
import java.time.*;

public class Logging {
    // Very abnormal to have a Library outside of com.libs, however, this library will never be needed outside of
    // com.networking

    public class Logger {
        public static void log(String from, String type, String data) {
            /*How to use:
            after calling NetLog.log(...);, ensure that the type is one of the following: INFO, WARN, ERROR, CRITICAL.
            Ensure that "from" is usually from the package and or child. For example,
            NetLog.log("api/Test", "INFO", "Hello World!"); would be the ideal `from` format to indicate that the
            message is being called within API inside of the Test.java file.
            
            it will show up in the file as "[XX-XX-XX] [API/Test] [INFO]: Hello World!" where the first brackets is the time
            the second set of brackets is the location and the type of log and afterwards is the message logged.

            all log files will have the LOG FILE INITIALIZED info at the top. it is recommended NOT to change this.
            */
            LocalTime now = LocalTime.now();

            int hours = now.getHour();
            int minutes = now.getMinute();
            int seconds = now.getSecond();

            try {
                FileWriter log = new FileWriter("Log-"+LocalDate.now()+".log", true);
                log.write("\n"+"["+hours+"-"+minutes+"-"+seconds+"]"+"["+from+"]"+"["+type+"]: "+data);
                log.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        }

        public static void initLogger() {
            // Recommended imediately at program startup.
            LocalTime now = LocalTime.now();

            int hours = now.getHour();
            int minutes = now.getMinute();
            int seconds = now.getSecond();

            try {
                FileWriter log = new FileWriter("Log-"+LocalDate.now()+".log");
                log.write("["+hours+"-"+minutes+"-"+seconds+"]"+"[libs/Logging] [INFO]: LOGGER INITIALIZED");
                log.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        }

    }
}
