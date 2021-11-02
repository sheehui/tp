package donnafin.logic.commands;

import donnafin.commons.core.Messages;
import donnafin.logic.PersonAdapter;
import donnafin.logic.parser.ClientViewParser;
import donnafin.logic.parser.EditCommandParser;
import donnafin.logic.parser.exceptions.ParseException;

public class ContactTabParser extends ClientViewParser {

    public ContactTabParser(PersonAdapter personAdapter) {
        super(personAdapter);
    }

    @Override
    protected Command tabSpecificHandler(String commandWord, String arguments) throws ParseException {
        switch (commandWord) {
        case EditCommand.COMMAND_WORD:
            return new EditCommandParser(personAdapter).parse(arguments);

        case AppendCommand.COMMAND_WORD:
            //fallthrough

        case RemoveCommand.COMMAND_WORD:
            throw new ParseException(Messages.MESSAGE_EDIT_COMMAND_SUPPORTED);

        default:
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
