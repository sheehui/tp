package donnafin.logic.parser;

import donnafin.commons.core.Messages;
import donnafin.commons.core.types.Index;
import donnafin.logic.commands.ViewCommand;
import donnafin.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns a ViewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args) throws ParseException {

        if (args.trim().equals("")) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        try {
            ParserUtil.checkIntegerMax(args);
            Index index = ParserUtil.parseIndex(args);
            return new ViewCommand(index);
        } catch (NumberFormatException e) {
            throw new ParseException(Messages.MESSAGE_INVALID_PERSON_INDEX);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE), pe);
        }
    }
}
