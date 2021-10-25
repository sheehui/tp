package donnafin.logic.parser;

import donnafin.logic.PersonAdapter;
import donnafin.logic.commands.Command;
import donnafin.logic.commands.ExitCommand;
import donnafin.logic.commands.HelpCommand;
import donnafin.logic.commands.HomeCommand;
import donnafin.logic.commands.SwitchTabCommand;
import donnafin.logic.parser.exceptions.ParseException;

abstract public class ClientViewParser extends ParserStrategy {

    protected final PersonAdapter personAdapter;

    public ClientViewParser(PersonAdapter personAdapter) {
        this.personAdapter = personAdapter;
    }

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
            return new SwitchTabCommand(ParserUtil.parseTab(arguments), personAdapter);

        default:
            return tabSpecificHandler(commandWord, arguments);
        }
    }

    abstract protected Command tabSpecificHandler(String commandWord, String arguments) throws ParseException;
}
