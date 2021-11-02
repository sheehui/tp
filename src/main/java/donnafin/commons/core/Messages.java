package donnafin.commons.core;

import donnafin.logic.commands.HelpCommand;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_USE_HELP_COMMAND = String.format(
            "Invalid command format! Try using the help command. \n%1$s", HelpCommand.MESSAGE_USAGE);
    public static final String MESSAGE_COMMAND_NOT_IN_HOMEWINDOW = "This is a client window command and is not "
            + "available in home window!\nTry using the view command to access client window commands."
            + "\nRefer to our user guide for more info.";
    public static final String MESSAGE_COMMAND_NOT_IN_CLIENTWINDOW = "This is a home window command and is not "
            + "available in client window!\nTry using the home command to access home window commands."
            + "\nRefer to our user guide for more info.";

}
