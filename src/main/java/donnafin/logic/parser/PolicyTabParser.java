package donnafin.logic.parser;

import donnafin.commons.core.Messages;
import donnafin.logic.commands.AddCommand;
import donnafin.logic.commands.Command;
import donnafin.logic.commands.DeleteCommand;
import donnafin.logic.parser.exceptions.ParseException;

public class PolicyTabParser extends ClientViewParser {

    @Override
    protected Command tabSpecificHandler(String commandWord, String arguments) throws ParseException {
        switch (commandWord) {
        case AddCommand.COMMAND_WORD_POLICY:
            return new AddCommandParser().parse(arguments);
        case DeleteCommand.COMMAND_WORD_POLICY:
            return new DeleteCommandParser().parse(arguments);
        case "EDIT": // parse the rest of it
        default: // throw error
            throw new ParseException(Messages.MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
