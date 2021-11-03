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
import donnafin.model.person.Person;
import donnafin.model.person.Policy;

public class PoliciesTabParserTest {
    private PersonAdapter personAdapter;
    private Model model;
    private Person person;
    private PolicyTabParser parser;

    @BeforeEach
    private void reset() {
        person = getTypicalPersons().get(0);
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), null);
        this.personAdapter = new PersonAdapter(model, person);
        parser = new PolicyTabParser(personAdapter);
    }

    @Test
    public void parserCommand_appendCommand() throws ParseException, CommandException {
        parser.tabSpecificHandler("append",
                        "append n/Diamond Policy i/AIA iv/$10000 pr/$200 c/$1000")
                .execute(model);
        Policy addedPolicy = new Policy("Diamond Policy", "AIA", "$10000",
                "$200", "$1000");
        assertTrue(model.getFilteredPersonList().get(0).getPolicies().contains(addedPolicy));
    }

    @Test
    public void parserCommand_appendCommandInvalidInput_throwsException() {
        assertThrows(ParseException.class, () -> parser.tabSpecificHandler("append",
                "i/AIA iv/$10000 pr/$200 c/$1000").execute(model));
        assertThrows(ParseException.class, () -> parser.tabSpecificHandler("append",
                "append i/AIA iv/$10000 pr/$200 c/$1000").execute(model));
        assertThrows(ParseException.class, () -> parser.tabSpecificHandler("append",
                "append n/Diamond Policy iv/$10000 pr/$200 c/$1000").execute(model));
        assertThrows(ParseException.class, () -> parser.tabSpecificHandler("append",
                "append n/Diamond Policy i/AIA pr/$200 c/$1000").execute(model));
        assertThrows(ParseException.class, () -> parser.tabSpecificHandler("append",
                "append n/Diamond Policy i/AIA iv/$10000 c/$1000").execute(model));
        assertThrows(ParseException.class, () -> parser.tabSpecificHandler("append",
                "append n/Diamond Policy i/AIA pr/$200 iv/$10000").execute(model));
    }

    @Test
    public void parserCommand_removeCommand() throws ParseException, CommandException {
        parser.tabSpecificHandler("append",
                        "append n/Diamond Policy i/AIA iv/$10000 pr/$200 c/$1000")
                .execute(model);
        Policy addedPolicy = new Policy("Diamond Policy", "AIA", "$10000",
                "$200", "$1000");
        parser.tabSpecificHandler("remove", "1").execute(model);
        assertFalse(model.getFilteredPersonList().get(0).getAssets().contains(addedPolicy));
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
