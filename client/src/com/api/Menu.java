package com.api;
//import java.util.Scanner;
import com.libs.ScreenLib;
import com.main.Data;

public class Menu {

    public static void main(String[] args) {
        // the code below is for self reference, and should be removed before release to save space.
        /*Data.CONFIG cache = new Data.CONFIG();
        cache.UserData = new Data.UserData();
        cache.UserData.UUID = "abc";*/
        /*System.out.println("Test");
        Scanner userInput = new Scanner(System.in);

        System.out.println("Please answer the following questions:");
        System.out.println("What is your username?:");
        String userName = userInput.nextLine();

        System.out.println(userName); 
        Data.USER_DATA.UUID = "abc123";
        Data.runTest();
        System.out.println(Data.USER_DATA.UUID+"[NEW]");*/
        System.out.println("Some Useless Text I don't like very much");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // restore interrupt status
        }
        //ScreenLib.Clear();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // restore interrupt status
        }
        System.out.println("text I actually want to print");
    }
}