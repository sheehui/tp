package donnafin.logic.parser;

import java.util.Objects;

import donnafin.commons.core.Messages;
import donnafin.logic.PersonAdapter;
import donnafin.logic.commands.AddCommand;
import donnafin.logic.commands.ClearCommand;
import donnafin.logic.commands.Command;
import donnafin.logic.commands.DeleteCommand;
import donnafin.logic.commands.ExitCommand;
import donnafin.logic.commands.FindCommand;
import donnafin.logic.commands.HelpCommand;
import donnafin.logic.commands.HomeCommand;
import donnafin.logic.commands.ListCommand;
import donnafin.logic.commands.SwitchTabCommand;
import donnafin.logic.commands.ViewCommand;
import donnafin.logic.parser.exceptions.ParseException;

public abstract class ClientViewParser extends ParserStrategy {

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

        case AddCommand.COMMAND_WORD:
            //fallthrough

        case DeleteCommand.COMMAND_WORD:
            //fallthrough

        case ClearCommand.COMMAND_WORD:
            //fallthrough

        case FindCommand.COMMAND_WORD:
            //fallthrough

        case ListCommand.COMMAND_WORD:
            //fallthrough

        case ViewCommand.COMMAND_WORD:
            throw new ParseException(Messages.MESSAGE_COMMAND_NOT_IN_CLIENT_WINDOW);

        default:
            return tabSpecificHandler(commandWord, arguments);
        }
    }

    protected abstract Command tabSpecificHandler(String commandWord, String arguments) throws ParseException;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientViewParser)) {
            return false;
        }
        ClientViewParser that = (ClientViewParser) o;
        return Objects.equals(personAdapter.getSubject(), that.personAdapter.getSubject());
    }

    @Override
    public int hashCode() {
        return Objects.hash(personAdapter);
    }
}
