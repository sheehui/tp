package donnafin.logic.parser;

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
import donnafin.model.person.Liability;
import donnafin.model.person.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static donnafin.testutil.TypicalPersons.getTypicalAddressBook;
import static donnafin.testutil.TypicalPersons.getTypicalPersons;
import static org.junit.jupiter.api.Assertions.*;

public class LiabilitiesTabParserTest {
    private PersonAdapter personAdapter;
    private Model model;
    private Person person;
    private LiabilitiesTabParser parser;

    @BeforeEach
    private void reset() {
        person = getTypicalPersons().get(0);
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), null);
        this.personAdapter = new PersonAdapter(model, person);
        parser = new LiabilitiesTabParser(personAdapter);
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
}
