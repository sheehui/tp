package donnafin.logic.parser;

import donnafin.commons.core.Messages;
import donnafin.logic.PersonAdapter;
import donnafin.logic.commands.AppendCommand;
import donnafin.logic.commands.Command;
import donnafin.logic.commands.RemoveCommand;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.ui.Ui;

public class PolicyTabParser extends ClientViewParser {

    public PolicyTabParser(PersonAdapter personAdapter) {
        super(personAdapter);
    }

    @Override
    protected Command tabSpecificHandler(String commandWord, String arguments) throws ParseException {
        switch (commandWord) {
        case AppendCommand.COMMAND_WORD:
            return new AppendCommandParser(Ui.ViewFinderState.POLICIES, super.personAdapter).parse(arguments);
        case RemoveCommand.COMMAND_WORD:
            return new RemoveCommandParser(Ui.ViewFinderState.POLICIES, super.personAdapter).parse(arguments);
        default: // throw error
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
