package donnafin.commons.core;

import donnafin.logic.commands.EditCommand;
import donnafin.logic.commands.HelpCommand;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_INDEX = "The client index provided is invalid!";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d clients listed!";
    public static final String MESSAGE_PERSON_LISTED_OVERVIEW = "1 client listed!";
    public static final String MESSAGE_NO_PERSON_LISTED_OVERVIEW = "No client listed!";
    public static final String MESSAGE_USE_HELP_COMMAND = String.format(
            "Invalid command format! Try using the help command. \n%1$s", HelpCommand.MESSAGE_USAGE);
    public static final String MESSAGE_COMMAND_NOT_IN_HOME_WINDOW = "This is a client window command and is not "
            + "available in home window!\nTry using the view command to access client window commands."
            + "\nRefer to our user guide for more info.";
    public static final String MESSAGE_COMMAND_NOT_IN_CLIENT_WINDOW = "This is a home window command and is not "
            + "available in client window!\nTry using the home command to access home window commands."
            + "\nRefer to our user guide for more info.";
    public static final String MESSAGE_EDIT_COMMAND_UNAVAILABLE = "Editing is not supported in current version of "
            + "DonnaFin.\nSupport for editing will be coming soon!";
    public static final String MESSAGE_EDIT_COMMAND_SUPPORTED = String.format(
            "Append and Remove commands are unavailable but the Edit command is supported for Contacts.\n%1$s",
            EditCommand.MESSAGE_USAGE);
    public static final String MESSAGE_NO_CLIENT_WINDOW_COMMANDS_SUPPORTED = "Append, Remove and Edit commands are "
            + "not supported in the Notes tab.\nYou may add your changes directly in the text box below.";
}
