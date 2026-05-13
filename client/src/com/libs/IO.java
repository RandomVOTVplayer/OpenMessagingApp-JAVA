package com.libs;

import java.util.Scanner;

public class IO {
    private static final Scanner userInput = new Scanner(System.in);

    private static class Store {
        static String input;
    }

    public static void write(String ask) { // Reads user input

        System.out.print(ask);
        Store.input = userInput.next();
    }
    public static String read(String type) { //writes stored input out. if Type is 'out', prints using System.out, Type 'return' returns the value in a String. 
    // in the future, put the entire check below inside of a if loop that checks for null in Store.input.
    if (type == "out") {
            if (Store.input != null) {
                System.out.println(Store.input);
            } else {
                throw new RuntimeException("Store.input returned null");
            }
        } else if (type == "return") {
            if (Store.input != null) {
                return(Store.input);
            } else {
                throw new RuntimeException("Store.input returned null");
            }
        }
        return("Make sure that 'out' and 'return' are lowercase - IO.java: Line 32");
    }
}