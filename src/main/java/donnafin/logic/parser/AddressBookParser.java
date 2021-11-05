package donnafin.logic.parser;

import donnafin.commons.core.Messages;
import donnafin.logic.commands.AddCommand;
import donnafin.logic.commands.AppendCommand;
import donnafin.logic.commands.ClearCommand;
import donnafin.logic.commands.Command;
import donnafin.logic.commands.DeleteCommand;
import donnafin.logic.commands.EditCommand;
import donnafin.logic.commands.ExitCommand;
import donnafin.logic.commands.FindCommand;
import donnafin.logic.commands.HelpCommand;
import donnafin.logic.commands.HomeCommand;
import donnafin.logic.commands.ListCommand;
import donnafin.logic.commands.RemoveCommand;
import donnafin.logic.commands.SwitchTabCommand;
import donnafin.logic.commands.ViewCommand;
import donnafin.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public final class AddressBookParser extends ParserStrategy {

    /**
     * Parse the user input given the command word and arguments.
     *
     * @param arguments a single string containing all the remaining arguments to user input.
     */
    public Command parseCommand(String commandWord, String arguments) throws ParseException {
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return !arguments.equals("") ? throwsInvalidInputMsg() : new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return !arguments.equals("") ? throwsInvalidInputMsg() : new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return !arguments.equals("") ? throwsInvalidInputMsg() : new ExitCommand();

        case ViewCommand.COMMAND_WORD:
            return new ViewCommandParser().parse(arguments);

        case HelpCommand.COMMAND_WORD:
            return !arguments.equals("") ? throwsInvalidInputMsg() : new HelpCommand();

        case HomeCommand.COMMAND_WORD:
            return !arguments.equals("") ? throwsInvalidInputMsg() : new HomeCommand();

        case AppendCommand.COMMAND_WORD:
            //fallthrough

        case RemoveCommand.COMMAND_WORD:
            //fallthrough

        case EditCommand.COMMAND_WORD:
            //fallthrough

        case SwitchTabCommand.COMMAND_WORD:
            throw new ParseException(Messages.MESSAGE_COMMAND_NOT_IN_HOME_WINDOW);

        default:
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
