package donnafin.logic.commands;

import donnafin.commons.core.Messages;
import donnafin.logic.PersonAdapter;
import donnafin.logic.parser.ClientViewParser;
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
            throw new ParseException(Messages.MESSAGE_NO_CLIENTWINDOW_COMMANDS_SUPPORTED);

        default:
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
