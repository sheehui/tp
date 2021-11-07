package donnafin.logic.parser;

import static donnafin.commons.core.Messages.MESSAGE_USE_HELP_COMMAND;
import static donnafin.logic.parser.ParserStrategyTestUtil.assertParseFailure;
import static donnafin.testutil.Assert.assertThrows;
import static donnafin.testutil.TypicalPersons.getTypicalAddressBook;
import static donnafin.testutil.TypicalPersons.getTypicalPersons;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import donnafin.commons.core.Messages;
import donnafin.logic.PersonAdapter;
import donnafin.logic.commands.AddCommand;
import donnafin.logic.commands.ClearCommand;
import donnafin.logic.commands.Command;
import donnafin.logic.commands.DeleteCommand;
import donnafin.logic.commands.ExitCommand;
import donnafin.logic.commands.FindCommand;
import donnafin.logic.commands.HelpCommand;
import donnafin.logic.commands.HomeCommand;
import donnafin.logic.commands.ListCommand;
import donnafin.logic.commands.SwitchTabCommand;
import donnafin.logic.commands.ViewCommand;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.model.Model;
import donnafin.model.ModelManager;
import donnafin.model.UserPrefs;
import donnafin.model.person.Person;

public class ClientViewParserTest {

    private ClientViewParser parser;
    private PersonAdapter personAdapter;

    @BeforeEach
    public void reset() {
        Person person = getTypicalPersons().get(0);
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), null);
        personAdapter = new PersonAdapter(model, person);
        parser = new ClientViewParser(personAdapter) {
            protected Command tabSpecificHandler(String commandWord, String arguments) throws ParseException {
                throw new ParseException(Messages.MESSAGE_COMMAND_NOT_IN_CLIENT_WINDOW);
            };
        };
    }

    @Test
    public void parseCommand_helpCommand_success() throws ParseException {
        String validArg = "";
        String invalidArg = "me";
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD, validArg) instanceof HelpCommand);
        //Help command input should consist of only the help command word, arguments should be empty
        assertThrows(ParseException.class, MESSAGE_USE_HELP_COMMAND, () ->
            parser.parseCommand(HelpCommand.COMMAND_WORD, invalidArg));
    }

    @Test
    public void parseCommand_homeCommand_success() throws ParseException {
        String validArg = "";
        String invalidArg = "meeting";
        assertTrue(parser.parseCommand(HomeCommand.COMMAND_WORD, validArg) instanceof HomeCommand);
        //Home command input should consist of only the home command word, arguments should be empty
        assertThrows(ParseException.class, MESSAGE_USE_HELP_COMMAND, () ->
            parser.parseCommand(HomeCommand.COMMAND_WORD, invalidArg));
    }

    @Test
    public void parseCommand_exitCommand_success() throws ParseException {
        String validArg = "";
        String invalidArg = "client";
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, validArg) instanceof ExitCommand);
        //Exit command input should consist of only the exit command word, arguments should be empty
        assertThrows(ParseException.class, MESSAGE_USE_HELP_COMMAND, () ->
            parser.parseCommand(ExitCommand.COMMAND_WORD, invalidArg));
    }

    @Test
    public void parseCommand_switchTabCommand_success() throws ParseException {
        String tab = "p";
        String invalidTab = "s";
        assertTrue(parser.parseCommand(SwitchTabCommand.COMMAND_WORD, tab) instanceof SwitchTabCommand);
        assertThrows(ParseException.class, () -> parser.parseCommand(SwitchTabCommand.COMMAND_WORD, invalidTab));
    }

    //The following tests ensures home window commands are not usable in client window
    //Error message also reflects as such.
    @Test
    public void parseCommand_clearCommand_failure() throws ParseException {
        assertNotNull(new AddressBookParser().parseCommand(ClearCommand.COMMAND_WORD));
        assertParseFailure(parser, ClearCommand.COMMAND_WORD, Messages.MESSAGE_COMMAND_NOT_IN_CLIENT_WINDOW);
    }

    @Test
    public void parseCommand_addCommand_failure() throws ParseException {
        String prefixedName = " n/john doe";
        String prefixedPhone = " p/29387983";
        String prefixedEmail = " e/john@doe.com";
        String prefixedAddress = " a/marina bay sands";
        String userInput = prefixedName + prefixedPhone + prefixedEmail + prefixedAddress;

        assertNotNull(new AddCommandParser().parse(userInput));
        assertParseFailure(parser, AddCommand.COMMAND_WORD + userInput,
                Messages.MESSAGE_COMMAND_NOT_IN_CLIENT_WINDOW);
    }

    @Test
    public void parseCommand_deleteCommand_failure() throws ParseException {
        String userInput = " 1";

        assertNotNull(new DeleteCommandParser().parse(userInput));
        assertParseFailure(parser, DeleteCommand.COMMAND_WORD + userInput,
                Messages.MESSAGE_COMMAND_NOT_IN_CLIENT_WINDOW);
    }

    @Test
    public void parseCommand_viewCommand_failure() throws ParseException {
        String userInput = " 1";

        assertNotNull(new ViewCommandParser().parse(userInput));
        assertParseFailure(parser, ViewCommand.COMMAND_WORD + userInput,
                Messages.MESSAGE_COMMAND_NOT_IN_CLIENT_WINDOW);
    }

    @Test
    public void parseCommand_findCommand_failure() throws ParseException {
        String userInput = "alex";

        assertNotNull(new FindCommandParser().parse(userInput));
        assertParseFailure(parser, FindCommand.COMMAND_WORD + userInput,
                Messages.MESSAGE_COMMAND_NOT_IN_CLIENT_WINDOW);
    }

    @Test
    public void parseCommand_listCommand_failure() throws ParseException {
        assertNotNull(new AddressBookParser().parseCommand(ListCommand.COMMAND_WORD));
        assertParseFailure(parser, ListCommand.COMMAND_WORD,
                Messages.MESSAGE_COMMAND_NOT_IN_CLIENT_WINDOW);
    }

}
