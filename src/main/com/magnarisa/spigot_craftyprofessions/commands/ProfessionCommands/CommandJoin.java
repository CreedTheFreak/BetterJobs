package com.magnarisa.spigot_craftyprofessions.commands.ProfessionCommands;

import com.magnarisa.spigot_craftyprofessions.commands.ProfessionCommand;
import com.magnarisa.spigot_craftyprofessions.container.CommandData;
import com.magnarisa.spigot_craftyprofessions.container.CraftyPlayer;

/**
 * This is the Join command for a Profession. If a player issues this command
 * it will join them into a Profession.
 */
public class CommandJoin extends ProfessionCommand
{
    /**
     * The Default Constructor is used to setup the information
     * surrounding the Profession Join Command
     */
    public CommandJoin ()
    {
       super (new CommandData (
            "join",
            "Joins the user to the specified Profession",
            "/prof join [ProfessionName]",
            "spigot_craftyprofessions.use.join"));
    }

    /**
     * This method executes the command for the given class.
     *
     * @param sender The Sender of the command
     *
     * @param args The arguments of the command.
     *
     * @return True  - If the command succeeds in any way including having not enough arguments or
     *                   if some exception was thrown.
     *         False - If the command fails all checks
     */
    public boolean execute (CraftyPlayer sender, String... args)
    {
        sender.sendMessage ("You have just executed /prof join");

        return true;
    }

    /**
     * This method returns the name of the command
     *
     * @return The name of the command after the prefix /prof
     */
    public String cmdName ()
    {
        return mCommandData.getCommandArg ();
    }
}
