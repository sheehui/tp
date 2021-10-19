package donnafin.logic.parser;

import donnafin.logic.commands.Command;
import donnafin.logic.parser.exceptions.ParseException;

/*
Interface to declare all strategic parser needs to have parseCommand command
 */
public interface ParserStrategy {

    public Command parseCommand(String userInput) throws ParseException;
}