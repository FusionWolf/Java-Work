package com.nocturnalsoftworks.streamserver.networking;

// REGION: Import statements
import com.nocturnalsoftworks.streamserver.core.Main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

// This class is passed the serverSocket and begins accepting on
// a new Thread. Once it gets a connection, it waits for another.
public class SocketConnect implements Runnable {
    Socket socket; // The socket used for client/server communication
    PrintWriter socketWriter = null;
    BufferedReader socketReader = null;

    // Constructor
    public SocketConnect(String ipAddress, int port) {
        try {
            this.socket  = new Socket(ipAddress, port);
            socketWriter = new PrintWriter(socket.getOutputStream(), true);
            socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    // Ran once the thread starts.
    public void run() {
        while(true) {
            // We want to continue to accept new connections, so don't let this loop end.
            try {
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

                String stringPacket = "1111";
                System.out.print("Type in your message: ");
                stringPacket += stdIn.readLine();

                socketWriter.write(stringPacket);

            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    private char[] constructPacket(String stringPacket) {
        char[] packet = new char[stringPacket.length()];
        for (int c = 0; c < stringPacket.length(); c++) {
            packet[c] = stringPacket.charAt(c);
        }
        return packet;
    }

}
