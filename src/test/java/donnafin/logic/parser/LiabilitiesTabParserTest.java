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
import donnafin.model.person.Liability;
import donnafin.model.person.Person;
import donnafin.storage.JsonAddressBookStorage;
import donnafin.storage.JsonUserPrefsStorage;
import donnafin.storage.Storage;
import donnafin.storage.StorageManager;

public class LiabilitiesTabParserTest {

    @TempDir
    public Path testFolder;

    private PersonAdapter personAdapter;
    private Model model;
    private Person person;
    private Storage storage;
    private LiabilitiesTabParser parser;

    @BeforeEach
    private void reset() {
        person = getTypicalPersons().get(0);
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storage = new StorageManager(addressBookStorage, userPrefsStorage);
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), storage);
        this.personAdapter = new PersonAdapter(model, person);
        parser = new LiabilitiesTabParser(personAdapter);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void parserCommand_appendCommand() throws ParseException, CommandException {
        parser.tabSpecificHandler(AppendCommand.COMMAND_WORD,
                        "append n/Property debt with DBS ty/debt v/$100000 r/10% annual interest")
                        .execute(model);
        Liability addedLiability = new Liability("Property debt with DBS", "debt", "$100000",
                "10% annual interest");
        assertTrue(model.getFilteredPersonList().get(0).getLiabilities().contains(addedLiability));
    }

    @Test
    public void parserCommand_appendCommandInvalidInput_throwsException() {
        assertThrows(ParseException.class, () -> parser.tabSpecificHandler(AppendCommand.COMMAND_WORD,
                "append ty/debt v/$100000 r/10% annual interest").execute(model));
        assertThrows(ParseException.class, () -> parser.tabSpecificHandler(AppendCommand.COMMAND_WORD,
                "append n/Property debt with DBS v/$100000 r/10% annual interest").execute(model));
        assertThrows(ParseException.class, () -> parser.tabSpecificHandler(AppendCommand.COMMAND_WORD,
                "append n/Property debt with DBS ty/debt r/10% annual interest").execute(model));
        assertThrows(ParseException.class, () -> parser.tabSpecificHandler(AppendCommand.COMMAND_WORD,
                "append n/Property debt with DBS ty/debt v/$100000").execute(model));
    }

    @Test
    public void parserCommand_removeCommand() throws ParseException, CommandException {
        parser.tabSpecificHandler(AppendCommand.COMMAND_WORD,
                        "append n/Property debt with DBS ty/debt v/$100000 r/10% annual interest")
                        .execute(model);
        Liability addedLiability = new Liability("Property debt with DBS", "debt", "$100000",
                "10% annual interest");
        parser.tabSpecificHandler(RemoveCommand.COMMAND_WORD, "1").execute(model);
        assertFalse(model.getFilteredPersonList().get(0).getAssets().contains(addedLiability));
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
