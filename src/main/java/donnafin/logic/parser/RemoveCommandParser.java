package donnafin.logic.parser;

import donnafin.commons.core.Messages;
import donnafin.commons.core.types.Index;
import donnafin.logic.PersonAdapter;
import donnafin.logic.PersonAdapter.PersonField;
import donnafin.logic.commands.RemoveCommand;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.ui.Ui;

public class RemoveCommandParser {

    private final PersonAdapter personAdapter;
    private final PersonField field;

    /**
     * The parser used to parse the input for the remove command.
     * @param currentTab the current tab that the user is viewing when the command is used.
     * @param personAdapter the person the user is currently viewing.
     * @throws ParseException if it is called in the wrong tab.
     */
    public RemoveCommandParser(Ui.ViewFinderState currentTab, PersonAdapter personAdapter) throws ParseException {
        this.personAdapter = personAdapter;
        switch (currentTab) {
        case POLICIES:
            field = PersonField.POLICIES;
            break;
        case ASSETS:
            field = PersonField.ASSETS;
            break;
        case LIABILITIES:
            field = PersonField.LIABILITIES;
            break;
        default:
            throw new ParseException("Invalid tab for append.");
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new RemoveCommand(personAdapter, field, index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, RemoveCommand.MESSAGE_USAGE), pe);
        }
    }

}
