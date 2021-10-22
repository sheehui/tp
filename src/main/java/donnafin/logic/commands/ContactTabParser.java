package donnafin.logic.commands;

import donnafin.commons.core.Messages;
import donnafin.logic.parser.ClientViewParser;
import donnafin.logic.parser.exceptions.ParseException;

public class ContactTabParser extends ClientViewParser {

    @Override
    protected Command tabSpecificHandler(String commandWord, String arguments) throws ParseException {
        switch (commandWord) {
        case "Insert Edit command here":
            //
        default:
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
