package donnafin.logic.commands;

import donnafin.commons.core.Messages;
import donnafin.logic.PersonAdapter;
import donnafin.logic.parser.ClientViewParser;
import donnafin.logic.parser.EditCommandParser;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.ui.Ui;

public class ContactTabParser extends ClientViewParser {

    public ContactTabParser(PersonAdapter personAdapter) {
        super(personAdapter);
    }

    @Override
    protected Command tabSpecificHandler(String commandWord, String arguments) throws ParseException {
        switch (commandWord) {
        case EditCommand.COMMAND_WORD:
            return new EditCommandParser(Ui.ViewFinderState.CONTACT, personAdapter).parse(arguments);
        default:
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
