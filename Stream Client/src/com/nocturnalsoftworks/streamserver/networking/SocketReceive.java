package com.nocturnalsoftworks.streamserver.networking;

// REGION: Import statements
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

// This class is passed the new client connection and begins to receive
// data from the client.
public class SocketReceive implements Runnable {
    Socket acceptedSocket; // Shadow copy of the newly accepted connection.

    public SocketReceive(Socket acceptedSocket) {
        this.acceptedSocket = acceptedSocket; // Create the shadow copy.

    }

    // Ran when the thread is started.
    public void run() {
        receiveData();
    }

    // This method tries to receive any data coming from the client's input stream.
    // It stores that data in a string and prints it out to the user...
    // TODO: Figure out how to send byte arrays...
    private void receiveData() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(acceptedSocket.getInputStream()));
            String temp = bufferedReader.readLine();

            char[] data = temp.toCharArray();

            if (data.length >= 4)
                PacketHandler.handle(data);
            else {
                System.out.println("Invalid packet length.");
            }
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
