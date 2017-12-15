package com.magnarisa.craftyprofessions.commands.ProfessionCommands;

import com.magnarisa.craftyprofessions.commands.ProfessionCommand;
import com.magnarisa.craftyprofessions.container.CommandData;
import com.magnarisa.craftyprofessions.container.CraftyPlayer;

/**
 * The CommandInfo class used to display information to the
 * sender of the command to display information about the
 * profession sent in as the argument.
 */
public class CommandInfo extends ProfessionCommand
{
    /**
     * The Default Constructor is used to setup the information
     * surrounding the Profession Join Command
     */
    public CommandInfo ()
    {
        super (new CommandData (
            "info",
            "Displays the information of a specific Profession",
            "/prof info [ProfessionName]",
            "craftyprofessions.use.info"));
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

        sender.sendMessage ("You have just executed /prof info");

        return true;
    }
}
