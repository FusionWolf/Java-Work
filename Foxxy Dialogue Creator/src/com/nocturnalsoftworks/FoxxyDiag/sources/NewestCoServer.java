package com.nocturnalsoftworks.FoxxyDiag.sources;

import com.nocturnalsoftworks.FoxxyDiag.core.Main;

import java.util.ArrayList;
import java.util.List;

public class NewestCoServer extends Source {
    // REGION: Source class variables
    private static String npcText;
    private static byte npcLinkNum;
    private static List<String> npcLinkList;

    public NewestCoServer(String npcText) {
        // Initialize the npcLinkList variable and npcText variable.
        npcLinkList = new ArrayList();
        this.npcText = npcText;
    }

    // This will need to be in the C# code no matter what, so we might as well have it in a method.
    public void generateNPCHeader() {
        // Change "case 100000" to whatever case number you need. Future editions of this program might
        // include a section to ask the user what case number the want and what NPCName they want.
        Main.addToDocument("#region NPCName\ncase 100000:");
        Main.addToDocument("{");
        Main.addToDocument("if (Control == 0)\n{");
        Main.addToDocument("GC.AddSend(Packets.NPCSay(\"" + npcText + "\"));");
    }

    // This will have to be put at the end of the C# code no matter what, so let's make a method for this too.
    public void generateNPCFooter() {
        // You can change the NPCSetFace(Number) in the C# source code.
        Main.addToDocument("GC.AddSend(Packets.NPCSetFace(30));");
        Main.addToDocument("GC.AddSend(Packets.NPCFinish());");
        Main.addToDocument("}\n break;");
        Main.addToDocument("}\n#endregion");
    }

    // This method takes every entry in the npcLinkList list and converts it to what it should be
    // for C# code.
    public void combineDialogue() {
        // We need the header because that always needs to be in the C# code for NewestCoServer.
        generateNPCHeader();

        // This string array is used to hold the two values that will come from the string we're about to split.
        // (read more on String.split() )
        String[] result;
        // These are how foreach loops are done in Java. For every String in the List of type String that we called
        // npcLinkList, give that string a variable and run the following code for it:
        for (String link : npcLinkList) {
            // Remember that | symbol earlier? We put it there because we had this format: NPCLinkText|NpcLinkOption...
            // well, in C# code, the option goes AFTER the text, so we need to grab those and keep them separate.
            // The NPCLink dialogue will make it to result[0], and the option number will end up at result[1].
            // String.split will not keep the | symbol.
            // Warning: If the user used the "|" symbol in their text, this will create problems, so be warned.
            result = link.split("|");
            // In C# code, it looks something like this: GC.AddSend(Packets.NPCLink(speech, linkNumber));
            // result[0] is the speech, result[1] is the link number.
            Main.addToDocument("GC.AddSend(Packets.NPCLink(\"" + result[0] + "\", " + result[1] + "));");
        }

        // Once we've looped for all those links, we generate the code that should go after that and add it
        // to the total document.
        generateNPCFooter();
    }

    // This method allows the user to create dialogue and a link number for however many
    // links the user wants to create.
    public void createNPCLinks() {
        Main.expandConsole();

        // Starting at the first link, keep looping this until you do this for however many
        // links the user wanted.
        for (int linkNum = 1; linkNum <= npcLinkNum; linkNum++) {
            // Create a temporary string. GIVE IT A VALUE! We're going to be adding onto this
            // variable, so it can't be null which is what happens if you do "String tempLink;"
            String tempLink = "";
            System.out.println("What would you like the text to say for NPCLink #" + linkNum + "?");

            // Add onto the end of the tempLink string whatever the user says.
            tempLink += Main.scanner.nextLine();

            // This loop is to make sure the user chooses a valid option.
            while (true) {
                System.out.print("How do you want to link this option? (0 for NPC Exit a.k.a 255 | Max value of: " + linkNum + "): ");

                // Like in other methods, get input from the user and try to parse it and store it.
                byte tempByte = Main.tryParseByte(Main.scanner.nextLine());
                // The user shouldn't be able to link an option to another link that won't exist, so don't allow it.
                // Also, 127 is my special number reserved for error catching.
                if (tempByte <= linkNum && tempByte != 127) {
                    if (tempByte == 0) {
                        // If the user enters "0", they want that to be the end of the NPC dialogue for that link.
                        // In every source I've ever seen, 255 designates the end of dialogue.

                        // We add the "|" symbol so we can split up the NPCLink text and the link option later.
                        // You'll see.
                        tempLink += "|" + 255;
                    } else {
                        // If the user doesn't enter 0 and the input is legal according to program's standards,
                        // add the | symbol and the link option.
                        tempLink += "|" + tempByte;
                    }

                    // Once this process is done, add it to the total list of Link dialogues.
                    npcLinkList.add(tempLink);
                    Main.expandConsole();
                    break;
                } else {
                    System.out.println("Invalid option.\n");
                }
            }

        }
    }

    // This method gets the desired number of NPCLink options the user wants to create and stores that number.
    public void getNPCLinks() {
        // This loop is so that if the user enters an invalid option, it repeats itself.
        while (true) {
            System.out.print("How many options would you like to give the user at the initial dialogue? (Maximum of 15): ");

            // Take the number of options the user wants and try to parse it as a byte and store it.
            byte tempByte = Main.tryParseByte(Main.scanner.nextLine());
            // As long as the tempByte shows 15 options or less and tempByte is not 127, continue.
            if (tempByte <= 15 && tempByte != 127) {
                // Stores that option into npcLinkNum which will be later used.
                npcLinkNum = tempByte;
                Main.expandConsole();
                break;
            } else {
                Main.expandConsole();
                System.out.println("That was an invalid option.\n\n");
            }
        }
    }
}
