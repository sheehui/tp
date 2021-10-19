package donnafin.logic.parser;

import static donnafin.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static donnafin.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static donnafin.commons.core.Messages.MESSAGE_USE_HELP_COMMAND;
import static donnafin.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import donnafin.logic.commands.ExitCommand;
import donnafin.logic.commands.HelpCommand;
import donnafin.logic.commands.HomeCommand;
import donnafin.logic.parser.exceptions.ParseException;

public class ClientViewParserTest {

    private final ClientViewParser parser = new ClientViewParser();

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_multipleWordsExit_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_USE_HELP_COMMAND, () -> parser.parseCommand("exit client"));
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_multipleWordsHelp_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_USE_HELP_COMMAND, () -> parser.parseCommand("help client"));
    }

    @Test
    public void parseCommand_home() throws Exception {
        assertTrue(parser.parseCommand(HomeCommand.COMMAND_WORD) instanceof HomeCommand);
    }

    @Test
    public void parseCommand_multipleWordsHome_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_USE_HELP_COMMAND, ()
            -> parser.parseCommand("home improvement works"));
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
