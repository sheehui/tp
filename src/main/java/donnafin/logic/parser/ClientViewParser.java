package donnafin.logic.parser;

import donnafin.commons.core.Messages;
import donnafin.logic.commands.Command;
import donnafin.logic.commands.ExitCommand;
import donnafin.logic.commands.HelpCommand;
import donnafin.logic.commands.HomeCommand;
import donnafin.logic.commands.SwitchTabCommand;
import donnafin.logic.parser.exceptions.ParseException;

public class ClientViewParser extends ParserStrategy {

    /**
     * Parse the user input given the command word and arguments.
     *
     * @param arguments a single string containing all the remaining arguments to user input.
     */
    @Override
    public Command parseCommand(String commandWord, String arguments) throws ParseException {
        switch (commandWord) {

        case HelpCommand.COMMAND_WORD:
            return !arguments.equals("") ? throwsInvalidInputMsg() : new HelpCommand();

        case HomeCommand.COMMAND_WORD:
            return !arguments.equals("") ? throwsInvalidInputMsg() : new HomeCommand();

        case ExitCommand.COMMAND_WORD:
            return !arguments.equals("") ? throwsInvalidInputMsg() : new ExitCommand();

        case SwitchTabCommand.COMMAND_WORD:
            return new SwitchTabCommand(ParserUtil.parseTab(arguments));

        default:
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
