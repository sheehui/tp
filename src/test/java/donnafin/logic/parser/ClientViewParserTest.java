package donnafin.logic.parser;

import donnafin.commons.core.Messages;
import donnafin.logic.PersonAdapter;
import donnafin.logic.commands.AddCommand;
import donnafin.logic.commands.ClearCommand;
import donnafin.logic.commands.Command;
import donnafin.logic.commands.DeleteCommand;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.model.Model;
import donnafin.model.ModelManager;
import donnafin.model.UserPrefs;
import donnafin.model.person.Person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static donnafin.testutil.TypicalPersons.getTypicalAddressBook;
import static donnafin.testutil.TypicalPersons.getTypicalPersons;
import static donnafin.logic.parser.ParserStrategyTestUtil.assertParseFailure;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientViewParserTest {

    private ClientViewParser parser;
    private AddressBookParser addressBookParser;

    @BeforeEach
    public void reset() {
        Person person = getTypicalPersons().get(0);
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), null);
        PersonAdapter personAdapter = new PersonAdapter(model, person);
        parser = new ClientViewParser(personAdapter) {
            protected Command tabSpecificHandler(String commandWord, String arguments) throws ParseException {
                return null;
            };
        };
        addressBookParser = new AddressBookParser();
    }

    @Test
    public void parseCommand_clearCommand_failure() {
        assertParseFailure(parser, ClearCommand.COMMAND_WORD, Messages.MESSAGE_COMMAND_NOT_IN_CLIENT_WINDOW);
    }

    @Test
    public void parseCommand_addCommand_failure() throws ParseException{
        String prefixedName = " n/john doe";
        String prefixedPhone = " p/29387983";
        String prefixedEmail = " n/john@doe.com";
        String prefixedAddress = " n/marina bay sands";
        String userInput =  prefixedName +  prefixedPhone + prefixedEmail + prefixedAddress;
        AddCommandParser addCommandParser = new AddCommandParser();

        assertTrue(addressBookParser.parseCommand(AddCommand.COMMAND_WORD, userInput) instanceof AddCommand);
        assertParseFailure(parser, userInput, Messages.MESSAGE_COMMAND_NOT_IN_CLIENT_WINDOW);
    }

    @Test
    public void parseCommand_deleteCommand_failure() throws ParseException{
        String index = " 1";
        String userInput = DeleteCommand.COMMAND_WORD + index;
        DeleteCommandParser deleteCommandParser = new DeleteCommandParser();

        assertNotNull(deleteCommandParser.parse(userInput));
        assertParseFailure(parser, userInput, Messages.MESSAGE_COMMAND_NOT_IN_CLIENT_WINDOW);
    }


}
