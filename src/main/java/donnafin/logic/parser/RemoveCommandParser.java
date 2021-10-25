package donnafin.logic.parser;

import donnafin.commons.core.Messages;
import donnafin.commons.core.types.Index;
import donnafin.logic.PersonAdapter;
import donnafin.logic.commands.RemoveCommand;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.ui.Ui;
import donnafin.ui.Ui.ClientViewTab;

import static donnafin.logic.parser.CliSyntax.PREFIX_COMMISSION;
import static donnafin.logic.parser.CliSyntax.PREFIX_INSURED_VALUE;
import static donnafin.logic.parser.CliSyntax.PREFIX_INSURER;
import static donnafin.logic.parser.CliSyntax.PREFIX_NAME;
import static donnafin.logic.parser.CliSyntax.PREFIX_REMARKS;
import static donnafin.logic.parser.CliSyntax.PREFIX_TYPE;
import static donnafin.logic.parser.CliSyntax.PREFIX_VALUE;
import static donnafin.logic.parser.CliSyntax.PREFIX_YEARLY_PREMIUM;

public class RemoveCommandParser {

    final private ClientViewTab currentTab;
    private final PersonAdapter personAdapter;

    public RemoveCommandParser(ClientViewTab currentTab, PersonAdapter personAdapter) throws ParseException {
        this.currentTab = currentTab;
        this.personAdapter = personAdapter;
        switch (currentTab) {
            case Policies:
                break;
            case Assets:
                break;
            case Liabilities:
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
            return new RemoveCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, RemoveCommand.MESSAGE_USAGE), pe);
        }
    }

}
