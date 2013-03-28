package com.nocturnalsoftworks.streamserver.utilities;


public class Conversion {

    public static byte tryParseByte(String byteObject) {
        // Takes the byte that was passed and attempts to parse it.
        try {
            return Byte.parseByte(byteObject);
        }
        // If it can't be parsed, it returns 127. That's basically saying
        // byteObject isn't valid to become a byte.
        catch(Exception ex) {
            return Byte.MAX_VALUE;
        }
    }

    public static short tryParseShort(String shortObject) {
        try {
            return Short.parseShort(shortObject);
        } catch (Exception ex) {
            return Short.MAX_VALUE;
        }
    }

    public static int tryParseInt(String intObject) {
        try {
            return Integer.parseInt(intObject);
        } catch (Exception ex) {
            return Integer.MAX_VALUE;
        }
    }

}
