package donnafin.logic.parser;

import static donnafin.testutil.TypicalPersons.getTypicalAddressBook;
import static donnafin.testutil.TypicalPersons.getTypicalPersons;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import donnafin.logic.PersonAdapter;
import donnafin.logic.commands.exceptions.CommandException;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.model.Model;
import donnafin.model.ModelManager;
import donnafin.model.UserPrefs;
import donnafin.model.person.Liability;
import donnafin.model.person.Person;

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
        parser.tabSpecificHandler("append",
                        "append n/Property debt with DBS ty/debt v/$100000 r/10% annual interest")
                        .execute(model);
        Liability addedLiability = new Liability("Property debt with DBS", "debt", "$100000",
                "10% annual interest");
        assertTrue(model.getFilteredPersonList().get(0).getLiabilities().contains(addedLiability));
    }

    @Test
    public void parserCommand_appendCommandInvalidInput_throwsException() {
        assertThrows(ParseException.class, () -> parser.tabSpecificHandler("append",
                "append ty/debt v/$100000 r/10% annual interest").execute(model));
        assertThrows(ParseException.class, () -> parser.tabSpecificHandler("append",
                "append n/Property debt with DBS v/$100000 r/10% annual interest").execute(model));
        assertThrows(ParseException.class, () -> parser.tabSpecificHandler("append",
                "append n/Property debt with DBS ty/debt r/10% annual interest").execute(model));
        assertThrows(ParseException.class, () -> parser.tabSpecificHandler("append",
                "append n/Property debt with DBS ty/debt v/$100000").execute(model));
    }

    @Test
    public void parserCommand_removeCommand() throws ParseException, CommandException {
        parser.tabSpecificHandler("append",
                        "append n/Property debt with DBS ty/debt v/$100000 r/10% annual interest")
                        .execute(model);
        Liability addedLiability = new Liability("Property debt with DBS", "debt", "$100000",
                "10% annual interest");
        parser.tabSpecificHandler("remove", "1").execute(model);
        assertFalse(model.getFilteredPersonList().get(0).getAssets().contains(addedLiability));
    }

    @Test
    public void parserCommand_removeCommandInvalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.tabSpecificHandler("remove", "p"));
    }

    @Test
    public void parserCommand_editCommand_throwsParseException() {
        assertThrows(ParseException.class, () ->
                parser.tabSpecificHandler("edit", "edit m/Benson").execute(model));
    }

    @Test
    public void parserCommand_invalidCommands_throwsParseException() {
        assertThrows(ParseException.class, () ->
                parser.tabSpecificHandler("view", "view 1").execute(model));
    }
}
