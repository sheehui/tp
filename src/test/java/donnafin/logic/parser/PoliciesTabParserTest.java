package donnafin.logic.parser;

import static donnafin.testutil.TypicalPersons.getTypicalAddressBook;
import static donnafin.testutil.TypicalPersons.getTypicalPersons;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import donnafin.logic.PersonAdapter;
import donnafin.logic.commands.AddCommand;
import donnafin.logic.commands.AppendCommand;
import donnafin.logic.commands.ClearCommand;
import donnafin.logic.commands.DeleteCommand;
import donnafin.logic.commands.EditCommand;
import donnafin.logic.commands.FindCommand;
import donnafin.logic.commands.HelpCommand;
import donnafin.logic.commands.HomeCommand;
import donnafin.logic.commands.ListCommand;
import donnafin.logic.commands.RemoveCommand;
import donnafin.logic.commands.ViewCommand;
import donnafin.logic.commands.exceptions.CommandException;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.model.Model;
import donnafin.model.ModelManager;
import donnafin.model.UserPrefs;
import donnafin.model.person.Person;
import donnafin.model.person.Policy;
import donnafin.storage.JsonAddressBookStorage;
import donnafin.storage.JsonUserPrefsStorage;
import donnafin.storage.Storage;
import donnafin.storage.StorageManager;

public class PoliciesTabParserTest {

    @TempDir
    public Path testFolder;

    private PersonAdapter personAdapter;
    private Model model;
    private Person person;
    private PolicyTabParser parser;
    private Storage storage;


    @BeforeEach
    private void reset() {
        person = getTypicalPersons().get(0);
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storage = new StorageManager(addressBookStorage, userPrefsStorage);
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), storage);
        this.personAdapter = new PersonAdapter(model, person);
        parser = new PolicyTabParser(personAdapter);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void parserCommand_appendCommand() throws ParseException, CommandException {
        parser.tabSpecificHandler(AppendCommand.COMMAND_WORD,
                        "append n/Diamond Policy i/AIA iv/$10000 pr/$200 c/$1000")
                .execute(model);
        Policy addedPolicy = new Policy("Diamond Policy", "AIA", "$10000",
                "$200", "$1000");
        assertTrue(model.getFilteredPersonList().get(0).getPolicies().contains(addedPolicy));
    }

    @Test
    public void parserCommand_appendCommandInvalidInput_throwsException() {
        assertThrows(ParseException.class, () -> parser.tabSpecificHandler(AppendCommand.COMMAND_WORD,
                "i/AIA iv/$10000 pr/$200 c/$1000").execute(model));
        assertThrows(ParseException.class, () -> parser.tabSpecificHandler(AppendCommand.COMMAND_WORD,
                "append i/AIA iv/$10000 pr/$200 c/$1000").execute(model));
        assertThrows(ParseException.class, () -> parser.tabSpecificHandler(AppendCommand.COMMAND_WORD,
                "append n/Diamond Policy iv/$10000 pr/$200 c/$1000").execute(model));
        assertThrows(ParseException.class, () -> parser.tabSpecificHandler(AppendCommand.COMMAND_WORD,
                "append n/Diamond Policy i/AIA pr/$200 c/$1000").execute(model));
        assertThrows(ParseException.class, () -> parser.tabSpecificHandler(AppendCommand.COMMAND_WORD,
                "append n/Diamond Policy i/AIA iv/$10000 c/$1000").execute(model));
        assertThrows(ParseException.class, () -> parser.tabSpecificHandler(AppendCommand.COMMAND_WORD,
                "append n/Diamond Policy i/AIA pr/$200 iv/$10000").execute(model));
    }

    @Test
    public void parserCommand_removeCommand() throws ParseException, CommandException {
        parser.tabSpecificHandler(AppendCommand.COMMAND_WORD,
                        "append n/Diamond Policy i/AIA iv/$10000 pr/$200 c/$1000")
                .execute(model);
        Policy addedPolicy = new Policy("Diamond Policy", "AIA", "$10000",
                "$200", "$1000");
        parser.tabSpecificHandler(RemoveCommand.COMMAND_WORD, "1").execute(model);
        assertFalse(model.getFilteredPersonList().get(0).getAssets().contains(addedPolicy));
    }

    @Test
    public void parserCommand_removeCommandInvalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.tabSpecificHandler(RemoveCommand.COMMAND_WORD, "p"));
    }

    @Test
    public void parserCommand_editCommand_throwsParseException() {
        assertThrows(ParseException.class, () ->
                parser.tabSpecificHandler(EditCommand.COMMAND_WORD, "").execute(model));
        assertThrows(ParseException.class, () ->
                parser.tabSpecificHandler(EditCommand.COMMAND_WORD, "edit").execute(model));
        assertThrows(ParseException.class, () ->
                parser.tabSpecificHandler(EditCommand.COMMAND_WORD, "edit m/Benson").execute(model));
    }

    @Test
    public void parserCommand_invalidCommands_throwsParseException() {
        assertThrows(ParseException.class, () ->
                parser.tabSpecificHandler(ViewCommand.COMMAND_WORD, "").execute(model));
        assertThrows(ParseException.class, () ->
                parser.tabSpecificHandler(ViewCommand.COMMAND_WORD, "view").execute(model));
        assertThrows(ParseException.class, () ->
                parser.tabSpecificHandler(ViewCommand.COMMAND_WORD, "view 1").execute(model));
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
    public void tabSpecificHandler_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, () ->
                parser.tabSpecificHandler("unknown command", "").execute(model));
    }
}
