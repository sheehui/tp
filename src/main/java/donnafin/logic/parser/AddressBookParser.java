package donnafin.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import donnafin.commons.core.Messages;
import donnafin.logic.commands.AddCommand;
import donnafin.logic.commands.ClearCommand;
import donnafin.logic.commands.Command;
import donnafin.logic.commands.DeleteCommand;
import donnafin.logic.commands.ExitCommand;
import donnafin.logic.commands.FindCommand;
import donnafin.logic.commands.HelpCommand;
import donnafin.logic.commands.ListCommand;
import donnafin.logic.commands.ViewCommand;
import donnafin.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser implements ParserStrategy {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
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

        default:
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
    }

    private Command throwsInvalidInputMsg() throws ParseException {
        throw new ParseException(Messages.MESSAGE_USE_HELP_COMMAND);
    }

}
