package donnafin.logic.parser;

import java.util.Objects;

import donnafin.commons.core.Messages;
import donnafin.commons.core.types.Index;
import donnafin.logic.PersonAdapter;
import donnafin.logic.PersonAdapter.PersonField;
import donnafin.logic.commands.RemoveCommand;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.ui.Ui;

public class RemoveCommandParser {

    public static final String MESSAGE_INVALID_TAB = "Invalid tab for remove.";

    private final PersonAdapter personAdapter;
    private final PersonField field;

    /**
     * The parser used to parse the input for the remove command.
     * @param currentTab the current tab that the user is viewing when the command is used.
     * @param personAdapter the person the user is currently viewing.
     * @throws ParseException if it is called in the wrong tab.
     */
    public RemoveCommandParser(Ui.ViewFinderState currentTab, PersonAdapter personAdapter) throws ParseException {
        Objects.requireNonNull(personAdapter);

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
            throw new ParseException(MESSAGE_INVALID_TAB);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveCommand parse(String args) throws ParseException {

        if (args.trim().equals("")) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, RemoveCommand.MESSAGE_USAGE));
        }

        try {
            ParserUtil.checkIntegerMax(args);
            Index index = ParserUtil.parseIndex(args);
            return new RemoveCommand(personAdapter, field, index);
        } catch (NumberFormatException e) {
            throw new ParseException(Messages.MESSAGE_INVALID_PERSON_INDEX);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, RemoveCommand.MESSAGE_USAGE), pe);
        }
    }

}
