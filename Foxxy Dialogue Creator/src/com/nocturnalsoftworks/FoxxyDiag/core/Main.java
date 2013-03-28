/*
             Title: Foxxy Dialogue Creator v1.5
     Author: xBlackPlagu3x (ElitePvPers)/ Kurama (4Botters)
               Language: Java (IntelliJ IDEA)

     This is an NPC Dialogue creator made with Java to display
     some basic programming and functionality. I am not a Java
     programmer, so this may not be 100% regarding on how this
     should be done, but I'm sure this will suffice just fine.

          Future versions may or may not include a GUI.

     As of now, this currently only supports dialogue from
     NewestCoServer (God help me). If you'd like to add more
     source options, you can add them in the sources package
     of this program. NewestCoServer is done for you, and it'd
     be a matter of copying NewestCoServer and editing it for
                      your favorite source.

                           Final Words?
     When I was programming this, I had about two more classes
     in mind, but since this is supposed to be basic Java, I
     decided to only use one class. I usually do things in a
                      more organized manner.

     Edit (3/27/2013): The final words have since changed. v1.3
     is the last version to have been basic. Now there are packages,
     multiple classes, inheritance, and all sorts of good stuff!
     Some of the comments may be outdated and need to be updated
             though. I'll work on that as I go along.
 */

// These are our imports. They are like using statements in C# and include statements in C++
package com.nocturnalsoftworks.FoxxyDiag.core;

import java.util.Scanner;

public class Main {
    // REGION: Build Variables
    private static final String Version = "v1.5";

    // REGION: Application Variables
    public static Scanner scanner;
    public static byte sourceChoice;
    public static String dialogueDoc = "";
    public static Dialogue dialogue;

    public static void main(String[] args) {
	    // Initialize the scanner.
        scanner = new Scanner(System.in);

        // I have created a program loop so that the user can choose when to exit the program
        // just in case they want to create multiple dialogues at a time.
        while (true) {

            // First I print the header of the program, which is a welcome message.
            printHeader();
            // Next I get which source the user wants to create dialogue for.
            sourceChoice = getDialogueChoice();

            // If they enter "0", they want to exit, so as long as dialogueChoice is not equal
            // to 0, create a new instance of core.Dialogue
            if (sourceChoice != 0) {
                // I had a core.Dialogue class for organization purposes and to show the use of classes + inheritance.
                dialogue = new Dialogue();
                // This passes what the dialogueChoice was to the method Dialogue.BeginDialogueCreation()
                dialogue.beginDialogueCreation(sourceChoice);

                // If the user entered "0" as their source choice, they want to exit.
            } else { System.exit(0); }

        }
    }

    // This method takes a passed string and adds it to the dialogueDoc variable which
    // is going to be wrote to a text file.
    public static void addToDocument(String toAdd) {
        dialogueDoc += toAdd + "\n";
    }

    // This method gets which (supported) source the user wants to create dialogue for.
    private static byte getDialogueChoice() {
        while (true) {
            // This prints out instructions.
            System.out.println("This program currently supports: NewestCoServer (1)\n");
            System.out.print("Please enter in the number corresponding to your source, or type '0' to exit and hit enter: ");

            // This grabs what the user entered into the console and attempts to parse it as a byte.
            // If it isn't a byte, tryParseByte returns 127. In that case, go back to the top of
            // the while loop.
            byte tempByte = tryParseByte(scanner.nextLine());

            // I use a switch statement because I like them better than a bunch of if statements.
            switch (tempByte) {
                // NewestCoServer source
                case 1:
                    System.out.println();
                    return 1;
                // Quit Program
                case 0:
                    expandConsole();
                    return 0;
                // Anything that doesn't have a case.
                default:
                    break;
            }
        }
    }

    // This method is used to determine with the byte can be parsed or not.
    // If not, return 127. In Java, 127 is the maximum size of a byte.
    // Since I don't want the user at any point to have 127 options, I chose
    // That number to use instead of any other number.
    public static byte tryParseByte(String byteObject) {
        // Takes the byte that was passed and attempts to parse it.
        try {
            return Byte.parseByte(byteObject);
        }
        // If it can't be parsed, it returns 127. That's basically saying
        // byteObject isn't valid to become a byte.
        catch(Exception ex) {
            return 127;
        }
    }

    // This method prints out the welcome message of my program.
    private static void printHeader() {
        System.out.println("Welcome to the Foxxy com.nocturnalsoftworks.FoxxyDiag.core.Dialogue Creator " + Version + "!\n");
    }

    /*
     * Because there is no "Console.Clear()" method like my main language C# has,
     * I decided to write this in place of it. In Java, System.out is primarily a
     * logging tool. When printing a line to the Console, you cannot take back what
     * you've already printed. Therefore, this will create new lines and give you a
     *                             blank screen.
     *
     * I have since edited this method, and right now, all this method will do is
     *     print a thick space of blank lines for an easier time reading.
     */
    public static void expandConsole() {
        for (int ln = 0; ln < 5; ln++)
            System.out.println();
    }
}
