package donnafin.logic.parser;

import static donnafin.testutil.TypicalPersons.GEORGE;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import donnafin.commons.exceptions.IllegalValueException;
import donnafin.logic.PersonAdapter;
import donnafin.logic.commands.RemoveCommand;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.model.AddressBook;
import donnafin.model.Model;
import donnafin.model.ModelManager;
import donnafin.model.UserPrefs;
import donnafin.storage.JsonAddressBookStorage;
import donnafin.storage.JsonUserPrefsStorage;
import donnafin.storage.StorageManager;
import donnafin.ui.Ui.ViewFinderState;

public class RemoveCommandParserTest {

    @TempDir
    public Path temporaryFolder;
    private PersonAdapter personAdapter;

    @BeforeEach
    public void setUp() throws IllegalValueException {
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        Model model = new ModelManager(new AddressBook(), new UserPrefs(), storage);
        model.addPerson(GEORGE);
        personAdapter = new PersonAdapter(model, GEORGE);
    }

    @Test
    public void constructor_validInput_success() throws ParseException {
        // a failure in constructor would result in an exception, not null so there is no need to check
        new RemoveCommandParser(ViewFinderState.ASSETS, personAdapter);
        new RemoveCommandParser(ViewFinderState.POLICIES, personAdapter);
        new RemoveCommandParser(ViewFinderState.LIABILITIES, personAdapter);
    }

    @Test
    public void constructor_invalidTab_throws() {
        assertThrows(ParseException.class, () ->
            new RemoveCommandParser(ViewFinderState.PERSON_LIST_PANEL, personAdapter));
        assertThrows(ParseException.class, () ->
            new RemoveCommandParser(ViewFinderState.CONTACT, personAdapter));
        assertThrows(ParseException.class, () ->
            new RemoveCommandParser(ViewFinderState.NOTES, personAdapter));
    }

    @Test
    public void constructor_nullInput_throws() {
        assertThrows(NullPointerException.class, () ->
            new RemoveCommandParser(ViewFinderState.POLICIES, null));
    }


    @Test
    public void parse_integer_success() throws ParseException {
        RemoveCommand ignored;

        // EP: 1 - MAX INT
        assertNotNull(new RemoveCommandParser(ViewFinderState.ASSETS, personAdapter).parse("1"));
        assertNotNull(
                new RemoveCommandParser(ViewFinderState.ASSETS, personAdapter).parse(Integer.MAX_VALUE + ""));
    }

    @Test
    public void parse_numbersTooSmallOrBig_throws() throws ParseException {
        RemoveCommandParser testSubject = new RemoveCommandParser(ViewFinderState.ASSETS, personAdapter);

        // EP: -VE INFINITY to -1
        assertThrows(ParseException.class, () -> testSubject.parse("-1"));
        assertThrows(ParseException.class, () -> testSubject.parse(((long) Integer.MIN_VALUE - 1) + ""));
        assertThrows(ParseException.class, () -> testSubject.parse(Long.MIN_VALUE + ""));

        // EP: 0
        assertThrows(ParseException.class, () -> testSubject.parse("0"));

        // EP: MAX INT + 1 to +VE INFINITY
        assertThrows(ParseException.class, () -> testSubject.parse(((long) Integer.MAX_VALUE + 1) + ""));
        assertThrows(ParseException.class, () -> testSubject.parse(Long.MAX_VALUE + ""));
    }
}
