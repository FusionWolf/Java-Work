package com.nocturnalsoftworks.streamserver.networking;

// REGION: Import statements
import java.net.ServerSocket;
import java.net.Socket;

// This class is passed the serverSocket and begins accepting on
// a new Thread. Once it gets a connection, it waits for another.
public class SocketConnect implements Runnable {
    ServerSocket serverSocket; // A shadow copy of core.Main.serverSocket
    Socket socket; // The socket used for client/server communication

    // Constructor
    public SocketConnect(ServerSocket serverSocket) {
        this.serverSocket = serverSocket; // Create the shadow copy.
    }

    // Ran once the thread starts.
    public void run() {
        // We want to continue to accept new connections, so don't let this loop end.
        while(true)
            acceptSocket();
    }

    // Tries to accept any connections and then begins the SocketReceive thread
    // and passes it the new connection.
    private void acceptSocket() {
        try {
            System.out.println("Waiting to accept a new connection.");
            socket = serverSocket.accept();
            System.out.println("Connection accepted.");

            (new Thread(new SocketReceive(socket))).start();
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }

}
