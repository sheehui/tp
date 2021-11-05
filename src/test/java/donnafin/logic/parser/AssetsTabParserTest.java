package donnafin.logic.parser;

import static donnafin.testutil.TypicalPersons.getTypicalAddressBook;
import static donnafin.testutil.TypicalPersons.getTypicalPersons;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import donnafin.logic.PersonAdapter;
import donnafin.logic.commands.AppendCommand;
import donnafin.logic.commands.EditCommand;
import donnafin.logic.commands.RemoveCommand;
import donnafin.logic.commands.ViewCommand;
import donnafin.logic.commands.exceptions.CommandException;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.model.Model;
import donnafin.model.ModelManager;
import donnafin.model.UserPrefs;
import donnafin.model.person.Asset;
import donnafin.model.person.Person;

public class AssetsTabParserTest {
    private PersonAdapter personAdapter;
    private Model model;
    private Person person;
    private AssetsTabParser parser;

    @BeforeEach
    private void reset() {
        person = getTypicalPersons().get(0);
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), null);
        this.personAdapter = new PersonAdapter(model, person);
        parser = new AssetsTabParser(personAdapter);
    }

    @Test
    public void parserCommand_appendCommand() throws CommandException, ParseException {
        parser.tabSpecificHandler(AppendCommand.COMMAND_WORD,
                "append n/Good Class Bungalow ty/Property v/$10000000 r/newly bought with bank loan")
                        .execute(model);
        Asset addedAsset = new Asset("Good Class Bungalow", "Property", "$10000000",
                "newly bought with bank loan");
        assertTrue(model.getFilteredPersonList().get(0).getAssets().contains(addedAsset));
    }

    @Test
    public void parserCommand_appendCommandInvalidInput_throwsException() {
        assertThrows(ParseException.class, () -> parser.tabSpecificHandler(AppendCommand.COMMAND_WORD,
                        "append ty/Property v/$10000000 r/newly bought with bank loan").execute(model));
        assertThrows(ParseException.class, () -> parser.tabSpecificHandler(AppendCommand.COMMAND_WORD,
                "append n/Good Class Bungalow v/$10000000 r/newly bought with bank loan").execute(model));
        assertThrows(ParseException.class, () -> parser.tabSpecificHandler("append",
                "append n/Good Class Bungalow ty/Property r/newly bought with bank loan").execute(model));
        assertThrows(ParseException.class, () -> parser.tabSpecificHandler(AppendCommand.COMMAND_WORD,
                "append n/Property debt with DBS ty/debt v/$100000").execute(model));
    }

    @Test
    public void parserCommand_removeCommand() throws CommandException, ParseException {
        parser.tabSpecificHandler(AppendCommand.COMMAND_WORD,
                        "append n/Good Class Bungalow ty/Property v/$10000000 r/newly bought with bank loan")
                        .execute(model);
        Asset addedAsset = new Asset("Good Class Bungalow", "Property", "$10000000",
                "newly bought with bank loan");
        parser.tabSpecificHandler(RemoveCommand.COMMAND_WORD, "1").execute(model);
        assertFalse(model.getFilteredPersonList().get(0).getAssets().contains(addedAsset));
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

}
