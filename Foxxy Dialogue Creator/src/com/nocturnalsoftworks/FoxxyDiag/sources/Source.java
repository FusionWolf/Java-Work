package com.nocturnalsoftworks.FoxxyDiag.sources;

public abstract class Source {

    // This will need to be in the C# code no matter what, so we might as well have it in a method.
    public abstract void generateNPCHeader();

    // This will have to be put at the end of the C# code no matter what, so let's make a method for this too.
    public abstract void generateNPCFooter();

    // This method takes every entry in the npcLinkList list and converts it to what it should be
    // for C# code.
    public abstract void combineDialogue();

    // This method allows the user to create dialogue and a link number for however many
    // links the user wants to create.
    public abstract void createNPCLinks();

    // This method gets the desired number of NPCLink options the user wants to create and stores that number.
    public abstract void getNPCLinks();
}
