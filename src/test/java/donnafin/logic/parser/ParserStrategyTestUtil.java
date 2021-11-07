package donnafin.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import donnafin.logic.commands.Command;
import donnafin.logic.parser.exceptions.ParseException;

public class ParserStrategyTestUtil {
    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand}.
     */
    public static void assertParseSuccess(ParserStrategy parser, String command, String args, Command expectedCommand) {
        try {
            Command temp = parser.parseCommand(command, args);
            assertEquals(temp, expectedCommand);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and the error message
     * equals to {@code expectedMessage}.
     */
    public static void assertParseFailure(ParserStrategy parser, String userInput, String expectedMessage) {
        try {
            parser.parseCommand(userInput);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }
}
