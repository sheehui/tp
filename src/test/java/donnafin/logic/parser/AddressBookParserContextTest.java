package donnafin.logic.parser;

import static donnafin.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static donnafin.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static donnafin.commons.core.Messages.MESSAGE_USE_HELP_COMMAND;
import static donnafin.testutil.Assert.assertThrows;
import static donnafin.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import donnafin.logic.commands.AddCommand;
import donnafin.logic.commands.ClearCommand;
import donnafin.logic.commands.DeleteCommand;
import donnafin.logic.commands.ExitCommand;
import donnafin.logic.commands.FindCommand;
import donnafin.logic.commands.HelpCommand;
import donnafin.logic.commands.HomeCommand;
import donnafin.logic.commands.ListCommand;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.model.person.NameContainsKeywordsPredicate;
import donnafin.model.person.Person;
import donnafin.testutil.PersonBuilder;
import donnafin.testutil.PersonUtil;

/**
This class should ensure that the commands for AddressBookParser pass AND
the commands for the rest of the parser, in this case on client view parser fails.
 */
public class AddressBookParserContextTest {

    private final AddressBookParser addressBookParser = new AddressBookParser();
    private ParserContext parserContext = new ParserContext(addressBookParser);

    @BeforeEach
    public void reset() {
        parserContext = new ParserContext(addressBookParser);
    }

    @Test
    public void test_executeParserStrategyCommand_addressBookParserAdd() throws ParseException {
        //Test add
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parserContext.executeParserStrategyCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }


    @Test
    public void executeParserStrategyCommand_addressBookParserClear() throws Exception {
        assertTrue(parserContext.executeParserStrategyCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
    }

    @Test
    public void executeParserStrategyCommand_multiWordClearCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_USE_HELP_COMMAND, ()
            -> parserContext.executeParserStrategyCommand(ClearCommand.COMMAND_WORD + " client"));
    }

    @Test
    public void executeParserStrategyCommand_addressBookParserDelete() throws Exception {
        DeleteCommand command = (DeleteCommand) parserContext.executeParserStrategyCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void executeParserStrategyCommand_addressBookParserExit() throws Exception {
        assertTrue(parserContext.executeParserStrategyCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
    }

    @Test
    public void executeParserStrategyCommand_multiWordExitCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_USE_HELP_COMMAND, ()
            -> parserContext.executeParserStrategyCommand(ExitCommand.COMMAND_WORD + " client"));
    }

    @Test
    public void executeParserStrategyCommand_addressBookParserFind() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parserContext.executeParserStrategyCommand(
                FindCommand.COMMAND_WORD + " " + String.join(" ", keywords));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void executeParserStrategyCommand_addressBookParserHelp() throws Exception {
        assertTrue(parserContext.executeParserStrategyCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
    }

    @Test
    public void executeParserStrategyCommand_multiWordHelpCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_USE_HELP_COMMAND, ()
            -> parserContext.executeParserStrategyCommand(HelpCommand.COMMAND_WORD + " client"));
    }

    @Test
    public void executeParserStrategyCommand_addressBookParserList() throws Exception {
        assertTrue(parserContext.executeParserStrategyCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
    }

    @Test
    public void executeParserStrategyCommand_multiWordListCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_USE_HELP_COMMAND, ()
            -> parserContext.executeParserStrategyCommand(ListCommand.COMMAND_WORD + " client info"));
    }

    @Test
    public void executeParserStrategyCommand_addressBookParserUnrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parserContext.executeParserStrategyCommand(""));
    }

    @Test
    public void executeParserStrategyCommand_addressBookParserUnknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, ()
            -> parserContext.executeParserStrategyCommand("unknownCommand"));
    }

    @Test
    public void executeParserStrategyCommand_clientParserHome() throws Exception {
        assertTrue(strategyIsAddressBookParser());
        assertTrue(parserContext.executeParserStrategyCommand(HomeCommand.COMMAND_WORD) instanceof HomeCommand);
    }

    @Test
    public void executeParserStrategyCommand_multiWordHomeCommand_throwsParseException() throws Exception {
        assertThrows(ParseException.class, MESSAGE_USE_HELP_COMMAND, ()
            -> parserContext.executeParserStrategyCommand(HomeCommand.COMMAND_WORD + " 3"));
    }

    @Test
    public void executeParserStrategyCommand_clientParserUnrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                -> parserContext.executeParserStrategyCommand(""));
    }

    @Test
    public void executeParserStrategyCommand_clientParserUnknownCommand_throwsParseException() {
        assertThrows(ParseException.class,
                MESSAGE_UNKNOWN_COMMAND, ()
                -> parserContext.executeParserStrategyCommand("unknownCommand"));
    }

    private boolean strategyIsAddressBookParser() {
        return parserContext.getCurrentParserStrategy().equals(addressBookParser);
    }
}
