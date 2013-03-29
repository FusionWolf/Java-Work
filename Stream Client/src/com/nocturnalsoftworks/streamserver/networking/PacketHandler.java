package com.nocturnalsoftworks.streamserver.networking;


import com.nocturnalsoftworks.streamserver.utilities.Conversion;

public class PacketHandler {
    private static int packetLength;
    private static short packetType;

    public static void handle(char[] packet) {
        getPacketHeader(packet);

        System.out.println("Handling packet type: " + packetType + " | packet length: " + packetLength);

        switch (packetType) {
            default:
                System.out.println("Handling unknown packet. Skipping.");
        }
    }

    private static void getPacketHeader(char[] packet) {
        packetLength = packet.length;

        String temp = "";
        for (int x = 0; x < 4; x++) {
            temp += x;
        }

        packetType = Conversion.tryParseShort(temp);
    }
}
