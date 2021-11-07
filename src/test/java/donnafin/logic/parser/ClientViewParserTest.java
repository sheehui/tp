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

public class ClientViewParserTest {

    private ClientViewParser parser;

    @BeforeEach
    public void reset() {
        Person person = getTypicalPersons().get(0);
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), null);
        PersonAdapter personAdapter = new PersonAdapter(model, person);
        parser = new ClientViewParser(personAdapter) {
            protected Command tabSpecificHandler(String commandWord, String arguments) throws ParseException {
                throw new ParseException(Messages.MESSAGE_COMMAND_NOT_IN_CLIENT_WINDOW);
            };
        };
    }

    @Test
    public void parseCommand_clearCommand_failure() {
        assertParseFailure(parser, ClearCommand.COMMAND_WORD, Messages.MESSAGE_COMMAND_NOT_IN_CLIENT_WINDOW);
    }

    @Test
    public void parseCommand_addCommand_failure() throws ParseException{
        String prefixedName = " n/john doe";
        String prefixedPhone = " p/29387983";
        String prefixedEmail = " e/john@doe.com";
        String prefixedAddress = " a/marina bay sands";
        String userInput =  prefixedName +  prefixedPhone + prefixedEmail + prefixedAddress;

        assertNotNull(new AddCommandParser().parse(userInput));
        assertParseFailure(parser, AddCommand.COMMAND_WORD + userInput, Messages.MESSAGE_COMMAND_NOT_IN_CLIENT_WINDOW);
    }

    @Test
    public void parseCommand_deleteCommand_failure() throws ParseException{
        String userInput = " 1";
        DeleteCommandParser deleteCommandParser = new DeleteCommandParser();

        assertNotNull(deleteCommandParser.parse(userInput));
        assertParseFailure(parser, DeleteCommand.COMMAND_WORD + userInput, Messages.MESSAGE_COMMAND_NOT_IN_CLIENT_WINDOW);
    }


}
