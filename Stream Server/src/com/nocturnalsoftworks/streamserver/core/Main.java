package com.nocturnalsoftworks.streamserver.core;

// REGION: Import statements
import com.nocturnalsoftworks.streamserver.networking.*;
import java.net.ServerSocket;
import java.util.Scanner;

public class Main {
    static Scanner scanner; // Used for debugging.
    static ServerSocket serverSocket; // This is the server's socket end.

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(2882); // Initialize the server socket.

            // Create a new Thread and have it run a new instance of the SocketConnect class
            // and pass it the serverSocket variable so it can begin accepting on a new thread.
            (new Thread(new SocketConnect(serverSocket))).start();

            // Run this loop so this program doesn't end.
            while (true)
                scanner.nextLine();
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
