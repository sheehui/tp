package donnafin.logic.commands;

import donnafin.commons.core.Messages;
import donnafin.logic.PersonAdapter;
import donnafin.logic.parser.ClientViewParser;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.model.person.Notes;

public class NotesTabParser extends ClientViewParser {

    public NotesTabParser(PersonAdapter personAdapter) {
        super(personAdapter);
    }

    @Override
    protected Command tabSpecificHandler(String commandWord, String arguments) throws ParseException {
        switch (commandWord) {
        case "INSERT COMMAND WORD FOR EDIT HERE":
        default:
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }

    }
}
