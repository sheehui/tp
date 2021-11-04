package donnafin.logic.parser;

import donnafin.logic.PersonAdapter;
import donnafin.logic.commands.*;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.model.Model;
import donnafin.model.ModelManager;
import donnafin.model.UserPrefs;
import donnafin.model.person.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static donnafin.commons.core.Messages.*;
import static donnafin.testutil.Assert.assertThrows;
import static donnafin.testutil.TypicalPersons.getTypicalAddressBook;
import static donnafin.testutil.TypicalPersons.getTypicalPersons;
import static org.junit.jupiter.api.Assertions.*;

public class ContactTabParserTest {


    private PersonAdapter personAdapter;
    private Model model;
    private Person person;
    private ContactTabParser parser;

    @BeforeEach
    private void reset() {
        person = getTypicalPersons().get(0);
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), null);
        this.personAdapter = new PersonAdapter(model, person);
        parser = new ContactTabParser(personAdapter);
    }
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
    public void parseCommand_switchTabCommand() throws ParseException {
        assertTrue(parser.parseCommand(SwitchTabCommand.COMMAND_WORD, "assets") instanceof SwitchTabCommand);
    }

    @Test
    public void parseCommand_defaultCase() throws ParseException {
        assertTrue(parser.parseCommand(EditCommand.COMMAND_WORD,
                "edit e/johndoe@email.com") instanceof EditCommand);
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

    @Test
    public void equals_returnsCorrectOutput() {
        ContactTabParser newInstance = new ContactTabParser(personAdapter);
        assertEquals(true, parser.equals(newInstance));
    }

    @Test
    public void equals_nullInstance_returnsCorrectOutput() {
        ContactTabParser nullInstance = null;
        assertFalse(parser.equals(nullInstance));
    }

    @Test
    public void hashCode_returnsValid_output() {
        assertEquals(Objects.hash(personAdapter), parser.hashCode());
    }
}
