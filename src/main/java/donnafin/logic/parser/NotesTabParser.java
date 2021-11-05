package donnafin.logic.parser;

import donnafin.commons.core.Messages;
import donnafin.logic.PersonAdapter;
import donnafin.logic.commands.AppendCommand;
import donnafin.logic.commands.Command;
import donnafin.logic.commands.EditCommand;
import donnafin.logic.commands.RemoveCommand;
import donnafin.logic.parser.exceptions.ParseException;

public class NotesTabParser extends ClientViewParser {

    public NotesTabParser(PersonAdapter personAdapter) {
        super(personAdapter);
    }

    @Override
    protected Command tabSpecificHandler(String commandWord, String arguments) throws ParseException {
        switch (commandWord) {
        case AppendCommand.COMMAND_WORD:
            //fallthrough

        case RemoveCommand.COMMAND_WORD:
            //fallthrough

        case EditCommand.COMMAND_WORD:
            throw new ParseException(Messages.MESSAGE_NO_CLIENT_WINDOW_COMMANDS_SUPPORTED);

        default:
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
