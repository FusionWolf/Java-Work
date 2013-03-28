/*
         There is something I want to say, first and foremost.
     This source is not coded to the best of my ability. There are
    ways of coding that would make this program much more nice, and
    would allow a lot better organization. However, this is to show
      basic Java programming, so I did it like this. For example:

    Instead of having a switch statement for each source option we
    want, we could have each source option be it's own class. We
    could have created a Source class and have each source option
    INHERIT from the Source class. Then we could put all of these
    methods like "generateNPCHeader" inside of the Source class.
    The use for doing that is because, GC.AddSend does not work
    for every source and will need to be changed depending on the
    source that you want to create dialogue for. Instead, you
            could have had case 1 be something like:

                    case 1:
                    {
                        NewestCoServer sourceOption = new NewestCoServer();
                        sourceOption.Handle();
                        break;
                    }

      And then it would have handled all of the NPC dialogue based
    on which source the user chose. Then you could have put separate
     generateNPCHeader methods in each class and it would have just
                made everything so much smoother.

    I might do that in future editions, but I'll always leave this
     here so you understand the importance of classes and whatnot.
 */
package com.nocturnalsoftworks.FoxxyDiag.core;

// REGION: Import statements
import com.nocturnalsoftworks.FoxxyDiag.sources.NewestCoServer;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Dialogue {

    // Class constructor
    public Dialogue() {
    }

    // This method is called in the core.Main class once the user has chosen
    // their desired source to work with. Any number with a case means
    // it's supported. Default means any number WITHOUT a case, is not.
    public void beginDialogueCreation(byte sourceChoice) {
        // Again, switch statements to me, are nicer than if statements.
        // I use a switch statement when I think I'll have a bunch of
        // if statements.
        switch(sourceChoice) {
            // REGION: NewestCoServer
            case 1:
                // This adds a sort of informational text to the document before adding
                // anything else. If you copy/paste this into C# (the text), it'll be a
                // comment.
                Main.addToDocument("/*\nWritten with Foxxy Dialogue Creator");
                Main.addToDocument("   Dialogue type: NewestCoServer\n*/");

                System.out.println("Enter in the text you wish your NPC to say upon being clicked:");
                // This takes whatever the user enters into the console and stores it in a variable.
                NewestCoServer source = new NewestCoServer(Main.scanner.nextLine());

                // Again, this is used to generate some blank lines.
                Main.expandConsole();

                // It's best to have multiple methods instead of putting all your code in one method.
                // This way it prevents you from having some giant method that is hard to understand
                // later down the road.

                // Get how many NPCLinks the user wants to create.
                source.getNPCLinks();

                // Creates the dialogue for those links based on how many links the user wants to create.
                source.createNPCLinks();

                // Combines the NPCText and NPCLinks and adds it all together.
                source.combineDialogue();

                Main.expandConsole();
                System.out.println("Attempting to dump text.");

                // Dumps the text. This method is basic IO handling.
                dumpDialogue();
                break;
            default:
                System.out.println("There is no support for that option.");
                break;
        }
    }

    // This method dumps the dialogue to a .txt file!
    // It includes an example of basic IO handling and the use of Dates in Java.
    private void dumpDialogue() {
        // Create a new DateFormat. Normally, people put SimpleDateFormat("yyyy/MM/dd HH:mm:ss"), but
        // that will create "2013/03/26 5:28:23" which contains invalid characters for a file name.
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        // Create a new date variable.
        Date date = new Date();

        // The next two variables are used for FileStreaming and whatnot. Read more on those.
        // It's a big part of IO. The printWriter variable is set to null because it must be initialized
        // BEFORE the try/catch block, but any other value than "null" being set, has to have a try/catch block around it.
        PrintWriter printWriter = null;
        FileWriter writer;

        // Java forces you to handle any possible exceptions for the previous two variables, so we handle it here.
        // We TRY the following, and CATCH any exceptions that may happen and handle them.
        try {
            // Create a new "File" variable and pass it the location of this "file".
            // In this case, it's a directory. System.getProperty("user.dir") will get the directory
            // that you ran this program from. It then adds \Dialogue to the end of it, because
            // that's the folder I'll be dumping to.
            File dumpLocaleDir = new File(System.getProperty("user.dir") + "\\Dialogue");
            // If this directory doesn't exist, create it. It's gotta exist before you can dump stuff to it.
            if (!dumpLocaleDir.exists()) {
                if (!dumpLocaleDir.mkdir()) {
                    throw new Exception("Error creating new directory: " + dumpLocaleDir.getAbsolutePath());
                }
            }

            // Create a new File variable and pass it the location of where you want to dump it to.
            // In this case, I want this file to be in the directory we specified earlier and have the File name
            // be "Dialogue [whatever the date + time is].txt"
            // This can probably later be something else like the name/ID of the NPC.
            File dumpLocale = new File(dumpLocaleDir.toString() + "\\Dialogue [" + dateFormat.format(date) + "].txt");

            // If this file doesn't exist, create it. I really hope it doesn't exist, because if it does,
            // you've gone back in time which isn't good. Also, if this file does by chance exist,
            // it'll APPEND to it. Meaning it'll add text to the end of the file.
            if (!dumpLocale.exists()) {
                // If it can't create the file, throw an exception because that's an error in our program.
                // It should be able to create the file no problemo. Otherwise, continue!
                if(!dumpLocale.createNewFile()) {
                    throw new Exception("Error creating new file: " + dumpLocale.getAbsolutePath());
                }
            }

            // Now we initialize the writer variable AFTER we've done our file checking.
            // We pass it the location/file name of the file we want to write to.
            writer = new FileWriter(dumpLocale);
            // Then we make printWriter and pass it the FileWriter variable.
            printWriter = new PrintWriter(writer);
            // Finally, we print all of the text we've saved in core.Main.dialogueDoc variable to the file.
            printWriter.print(Main.dialogueDoc);
            Main.expandConsole();
            System.out.println("Your NPC dialogue was dumped at: " + dumpLocale);
            Main.expandConsole();
        }
        // If there was an error, report it to the user.
        catch (Exception ex) {
            Main.expandConsole();
            System.out.println("Your NPC dialogue could not be saved due to an error.");
            System.out.println(ex);
        }
        // Whether we successfully dump the file or not, we need to close the printWriter stream.
        finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }
}
