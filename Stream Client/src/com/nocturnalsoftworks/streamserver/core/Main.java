package com.nocturnalsoftworks.streamserver.core;

// REGION: Import statements
import com.nocturnalsoftworks.streamserver.networking.*;
import java.net.ServerSocket;
import java.util.Scanner;

public class Main {
    public static Scanner scanner; // Used for debugging.

    public static void main(String[] args) {
        try {
            scanner = new Scanner(System.in);

            // Create a new Thread and have it run a new instance of the SocketConnect class
            // and pass it the serverSocket variable so it can begin accepting on a new thread.
            (new Thread(new SocketConnect("localhost", 2882))).start();

            // Run this loop so this program doesn't end.
            while (true)
                scanner.nextLine();
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
