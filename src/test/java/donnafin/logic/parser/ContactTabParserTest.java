package donnafin.logic.parser;

import static donnafin.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static donnafin.commons.core.Messages.MESSAGE_USE_HELP_COMMAND;
import static donnafin.testutil.Assert.assertThrows;
import static donnafin.testutil.TypicalPersons.getTypicalAddressBook;
import static donnafin.testutil.TypicalPersons.getTypicalPersons;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import donnafin.logic.PersonAdapter;
import donnafin.logic.commands.AddCommand;
import donnafin.logic.commands.AppendCommand;
import donnafin.logic.commands.ClearCommand;
import donnafin.logic.commands.DeleteCommand;
import donnafin.logic.commands.EditCommand;
import donnafin.logic.commands.ExitCommand;
import donnafin.logic.commands.FindCommand;
import donnafin.logic.commands.HelpCommand;
import donnafin.logic.commands.HomeCommand;
import donnafin.logic.commands.ListCommand;
import donnafin.logic.commands.RemoveCommand;
import donnafin.logic.commands.SwitchTabCommand;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.model.Model;
import donnafin.model.ModelManager;
import donnafin.model.UserPrefs;
import donnafin.model.person.Person;

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
    public void parseCommand_multiFieldUpdate() throws ParseException {
        assertTrue(parser.parseCommand(EditCommand.COMMAND_WORD,
                "e/johndoe@email.com p/92837489") instanceof EditCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void equals_returnsCorrectOutput() {
        ContactTabParser newInstance = new ContactTabParser(personAdapter);
        assertTrue(parser.equals(newInstance));
    }

    @Test
    public void equals_nullInstance_returnsCorrectOutput() {
        ContactTabParser nullInstance = null;
        assertNotEquals(parser, nullInstance);
    }

    @Test
    public void hashCode_returnsValid_output() {
        assertEquals(Objects.hash(personAdapter), parser.hashCode());
    }

    @Test
    public void tabSpecificHandler_addCommand_throwsParseException() {
        assertThrows(ParseException.class, () ->
                parser.tabSpecificHandler(AddCommand.COMMAND_WORD, "n/john p/998 e/john@mail.com a/cck")
                        .execute(model));
    }

    @Test
    public void tabSpecificHandler_deleteCommand_throwsParseException() {
        assertThrows(ParseException.class, () ->
                parser.tabSpecificHandler(DeleteCommand.COMMAND_WORD, "1").execute(model));
    }

    @Test
    public void tabSpecificHandler_listCommand_throwsParseException() {
        assertThrows(ParseException.class, () ->
                parser.tabSpecificHandler(ListCommand.COMMAND_WORD, "").execute(model));
    }

    @Test
    public void tabSpecificHandler_findCommand_throwsParseException() {
        assertThrows(ParseException.class, () ->
                parser.tabSpecificHandler(FindCommand.COMMAND_WORD, "alex").execute(model));
    }

    @Test
    public void tabSpecificHandler_clearCommand_throwsParseException() {
        assertThrows(ParseException.class, () ->
                parser.tabSpecificHandler(ClearCommand.COMMAND_WORD, "").execute(model));
    }

    @Test
    public void tabSpecificHandler_homeCommand_throwsParseException() {
        assertThrows(ParseException.class, () ->
                parser.tabSpecificHandler(HomeCommand.COMMAND_WORD, "").execute(model));
    }

    @Test
    public void tabSpecificHandler_helpCommand_throwsParseException() {
        assertThrows(ParseException.class, () ->
                parser.tabSpecificHandler(HelpCommand.COMMAND_WORD, "").execute(model));
    }

    @Test
    public void tabSpecificHandler_exitCommand_throwsParseException() {
        assertThrows(ParseException.class, () ->
                parser.tabSpecificHandler(HelpCommand.COMMAND_WORD, "").execute(model));
    }

    @Test
    public void tabSpecificHandler_appendCommand_throwsParseException() {
        assertThrows(ParseException.class, () ->
                parser.tabSpecificHandler(AppendCommand.COMMAND_WORD, "n/asset ty/house v/$10000 r/debt")
                        .execute(model));
    }

    @Test
    public void tabSpecificHandler_removeCommand_throwsParseException() {
        assertThrows(ParseException.class, () ->
                parser.tabSpecificHandler(RemoveCommand.COMMAND_WORD, "1").execute(model));
    }

    @Test
    public void tabSpecificHandler_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, () ->
                parser.tabSpecificHandler("unknown command", "").execute(model));
    }
}
