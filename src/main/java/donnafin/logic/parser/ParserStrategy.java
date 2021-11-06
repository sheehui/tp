//@@author Bluntsord
package donnafin.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import donnafin.commons.core.Messages;
import donnafin.logic.commands.Command;
import donnafin.logic.commands.HelpCommand;
import donnafin.logic.parser.exceptions.ParseException;

/**
Interface to declare all strategic parser needs to have parseCommand command
 */
public abstract class ParserStrategy {

    /** Used for initial separation of command word and args. */
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

        final String commandWord = matcher.group("commandWord").toLowerCase();
        final String arguments = matcher.group("arguments");
        return parseCommand(commandWord, arguments);
    }

    public abstract Command parseCommand(String commandWord, String arguments) throws ParseException;

    protected Command throwsInvalidInputMsg() throws ParseException {
        throw new ParseException(Messages.MESSAGE_USE_HELP_COMMAND);
    }
}
