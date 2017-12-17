package com.magnarisa.spigot_craftyprofessions.commands;

import com.magnarisa.spigot_craftyprofessions.container.CraftyPlayer;

/**
 * TODO: I need to do what the below comment states
 *
 * I need to either add in the PlayerManager Reference to handle players
 * or use the Player Manager to retrieve the CraftyPlayer to pass into the
 * command rather than the Whole Manager.
 */
public interface ICommand
{
    /**
     * This is the primary method for executing a command.
     */
    boolean execute (CraftyPlayer sender, String... args);

    /**
     * This method returns the Name of the command.
     */
    String cmdName ();

    /**
     * Checks a users permissions
     */
    boolean checkPermission (CraftyPlayer player);

    /**
     * Grabs the description of the command
     */
    String getDescription ();

    /**
     * Grabs the usage of the command
     */
    String getUsage ();
}
