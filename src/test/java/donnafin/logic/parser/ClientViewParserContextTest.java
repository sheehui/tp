package donnafin.logic.parser;

import static donnafin.commons.core.Messages.MESSAGE_COMMAND_NOT_IN_CLIENT_WINDOW;
import static donnafin.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static donnafin.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static donnafin.commons.core.Messages.MESSAGE_USE_HELP_COMMAND;
import static donnafin.testutil.Assert.assertThrows;
import static donnafin.testutil.TypicalPersons.getTypicalAddressBook;
import static donnafin.testutil.TypicalPersons.getTypicalPersons;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import donnafin.logic.PersonAdapter;
import donnafin.logic.commands.ClearCommand;
import donnafin.logic.commands.DeleteCommand;
import donnafin.logic.commands.ExitCommand;
import donnafin.logic.commands.FindCommand;
import donnafin.logic.commands.HelpCommand;
import donnafin.logic.commands.HomeCommand;
import donnafin.logic.commands.ListCommand;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.model.Model;
import donnafin.model.ModelManager;
import donnafin.model.UserPrefs;
import donnafin.model.person.Person;
import donnafin.testutil.PersonBuilder;
import donnafin.testutil.PersonUtil;

/**
This class will act as the test class for parserContext when parserContext's ParserStrategy is ClientViewParser
It should fail the test for AddressBookParser.
 */
public class ClientViewParserContextTest {

    private ContactTabParser contactTabParser;
    private ParserContext parserContext;

    @BeforeEach
    public void reset() {
        Person person = getTypicalPersons().get(0);
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), null);
        PersonAdapter personAdapter = new PersonAdapter(model, person);
        contactTabParser = new ContactTabParser(personAdapter);
        parserContext = new ParserContext(contactTabParser);
        assertTrue(strategyIsClientParser());
    }

    @Test
    public void parseCommand_clientParserExit() throws Exception {
        assertTrue(parserContext.executeParserStrategyCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_multipleWordsExitCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_USE_HELP_COMMAND, ()
            -> contactTabParser.parseCommand(ExitCommand.COMMAND_WORD, "2"));
    }

    @Test
    public void executeParserStrategyCommand_clientParserHelp() throws Exception {
        assertTrue(parserContext.executeParserStrategyCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_multipleWordsHelpCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_USE_HELP_COMMAND, ()
            -> contactTabParser.parseCommand(HelpCommand.COMMAND_WORD, "us"));
    }

    @Test
    public void executeParserStrategyCommand_clientParserHome() throws Exception {
        assertTrue(parserContext.executeParserStrategyCommand(HomeCommand.COMMAND_WORD) instanceof HomeCommand);
    }

    @Test
    public void parseCommand_multipleWordsHomeCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_USE_HELP_COMMAND, ()
            -> contactTabParser.parseCommand("home sweet home"));
    }

    @Test
    public void executeParserStrategyCommand_clientParserUnrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), ()
                -> parserContext.executeParserStrategyCommand(""));
    }

    @Test
    public void executeParserStrategyCommand_clientParserUnknownCommand_throwsParseException() {
        assertThrows(ParseException.class,
            MESSAGE_UNKNOWN_COMMAND, ()
                -> parserContext.executeParserStrategyCommand("unknownCommand"));
    }

    //From here on out we should check that AddressBookParser command fails
    @Test
    public void test_executeParserStrategyCommand_addressBookParserAdd() {
        Person person = new PersonBuilder().build();
        assertThrows(ParseException.class,
                MESSAGE_COMMAND_NOT_IN_CLIENT_WINDOW, ()
                -> parserContext.executeParserStrategyCommand(PersonUtil.getAddCommand(person)));
    }


    @Test
    public void executeParserStrategyCommand_addressBookParserClear() {
        assertThrows(ParseException.class,
                MESSAGE_COMMAND_NOT_IN_CLIENT_WINDOW, ()
                -> parserContext.executeParserStrategyCommand(ClearCommand.COMMAND_WORD));
        assertThrows(ParseException.class,
            MESSAGE_UNKNOWN_COMMAND, ()
                -> parserContext.executeParserStrategyCommand(ClearCommand.COMMAND_WORD + "3"));
    }

    @Test
    public void executeParserStrategyCommand_addressBookParserDelete() {
        assertThrows(ParseException.class,
                MESSAGE_COMMAND_NOT_IN_CLIENT_WINDOW, ()
                -> parserContext.executeParserStrategyCommand(DeleteCommand.COMMAND_WORD));
        assertThrows(ParseException.class,
            MESSAGE_UNKNOWN_COMMAND, ()
                -> parserContext.executeParserStrategyCommand(DeleteCommand.COMMAND_WORD + "3"));
    }

    @Test
    public void executeParserStrategyCommand_addressBookParserFind() {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        String userInput = FindCommand.COMMAND_WORD + " " + String.join(" ", keywords);
        assertThrows(ParseException.class,
                MESSAGE_COMMAND_NOT_IN_CLIENT_WINDOW, ()
                -> parserContext.executeParserStrategyCommand(userInput));
    }

    @Test
    public void executeParserStrategyCommand_addressBookParserList() {
        assertThrows(ParseException.class,
                MESSAGE_COMMAND_NOT_IN_CLIENT_WINDOW, ()
                -> parserContext.executeParserStrategyCommand(ListCommand.COMMAND_WORD));
    }

    private boolean strategyIsClientParser() {
        return parserContext.getCurrentParserStrategy().equals(contactTabParser);
    }
}
