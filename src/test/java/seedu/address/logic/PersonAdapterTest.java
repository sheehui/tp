package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;

public class PersonAdapterTest {

    @TempDir
    public Path temporaryFolder;
    private PersonAdapter personAdapter;

    @BeforeEach
    public void setUp() {
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        Model model = new ModelManager(new AddressBook(), new UserPrefs(), storage);
        personAdapter = new PersonAdapter(model, ALICE);
    }

    @Test
    public void constructor() {
        assertEquals(ALICE, personAdapter.getSubject());
    }

    @Test
    public void getAllAttributesList_returnsCorrectOutput() {
        assertEquals(ALICE.getAllAttributesList(), personAdapter.getAllAttributesList());
    }

    @Test
    public void editPersonName_changesSubject() throws InvalidFieldException {
        personAdapter.edit(PersonAdapter.PersonField.NAME, "Peter");

        assertEquals(new Person(new Name("Peter"), ALICE.getPhone(), ALICE.getEmail(),
                ALICE.getAddress(), ALICE.getTags()), personAdapter.getSubject());
        assertNotEquals(ALICE, personAdapter.getSubject());

        personAdapter.edit(PersonAdapter.PersonField.NAME, "Alice Pauline");
        assertEquals(ALICE, personAdapter.getSubject());
    }

    @Test
    public void editPersonName_withIncorrectNameFormat_throwsInvalidFieldException() {
        String emptyName = " "; // empty string
        String invalidName = "Alice*"; // contains non-alphanumeric characters
        assertThrows(InvalidFieldException.class, () -> personAdapter.edit(PersonAdapter.PersonField.NAME, emptyName));
        assertThrows(InvalidFieldException.class, () ->
                personAdapter.edit(PersonAdapter.PersonField.NAME, invalidName));
    }

    @Test
    public void editPersonPhone_changesSubject() throws InvalidFieldException {
        personAdapter.edit(PersonAdapter.PersonField.PHONE, "90538978");

        assertEquals(new Person(ALICE.getName(), new Phone("90538978"),
                ALICE.getEmail(), ALICE.getAddress(), ALICE.getTags()), personAdapter.getSubject());
        assertNotEquals(ALICE, personAdapter.getSubject());

        personAdapter.edit(PersonAdapter.PersonField.PHONE, ALICE.getPhone().toString());
        assertEquals(ALICE, personAdapter.getSubject());
    }

    @Test
    public void editPersonPhone_withIncorrectPhoneFormat_throwsInvalidFieldException() {
        String emptyPhone = " "; // empty string
        String invalidPhone = "91"; // less than 3 numbers
        String invalidPhoneAlt = "9011p041"; // alphabets within digits
        assertThrows(InvalidFieldException.class, () ->
                personAdapter.edit(PersonAdapter.PersonField.PHONE, emptyPhone));
        assertThrows(InvalidFieldException.class, () ->
                personAdapter.edit(PersonAdapter.PersonField.PHONE, invalidPhone));
        assertThrows(InvalidFieldException.class, () ->
                personAdapter.edit(PersonAdapter.PersonField.PHONE, invalidPhoneAlt));
    }

    @Test
    public void editPersonEmail_changesSubject() throws InvalidFieldException {
        personAdapter.edit(PersonAdapter.PersonField.EMAIL, "alice29@email.com");

        assertEquals(new Person(ALICE.getName(), ALICE.getPhone(), new Email("alice29@email.com"),
                ALICE.getAddress(), ALICE.getTags()), personAdapter.getSubject());
        assertNotEquals(ALICE, personAdapter.getSubject());

        personAdapter.edit(PersonAdapter.PersonField.EMAIL, ALICE.getEmail().toString());
        assertEquals(ALICE, personAdapter.getSubject());
    }

    @Test
    public void editPersonEmail_withIncorrectPhoneFormat_throwsInvalidFieldException() {
        String missingLocalPart = "@example.com"; //missing local part
        String missingAtSymbol = "peterjackexample.com"; //missing '@' symbol
        String missingDomainName = "peterjack@"; //missing domain name

        assertThrows(InvalidFieldException.class, () ->
                personAdapter.edit(PersonAdapter.PersonField.EMAIL, missingLocalPart));
        assertThrows(InvalidFieldException.class, () ->
                personAdapter.edit(PersonAdapter.PersonField.EMAIL, missingAtSymbol));
        assertThrows(InvalidFieldException.class, () ->
                personAdapter.edit(PersonAdapter.PersonField.EMAIL, missingDomainName));
    }

    @Test
    public void editPersonAddress_changesSubject() throws InvalidFieldException {
        personAdapter.edit(PersonAdapter.PersonField.ADDRESS, "28 College Ave E, #B1-01, Singapore 138598");

        assertEquals(new Person(ALICE.getName(), ALICE.getPhone(), ALICE.getEmail(),
                new Address("28 College Ave E, #B1-01, Singapore 138598"),
                ALICE.getTags()), personAdapter.getSubject());
        assertNotEquals(ALICE, personAdapter.getSubject());

        personAdapter.edit(PersonAdapter.PersonField.ADDRESS, ALICE.getAddress().toString());
        assertEquals(ALICE, personAdapter.getSubject());
    }

    @Test
    public void editPersonAddress_withIncorrectPhoneFormat_throwsInvalidFieldException() {
        String emptyAddress = ""; //empty String
        String spacesOnly = " "; //spaces only

        assertThrows(InvalidFieldException.class, () ->
                personAdapter.edit(PersonAdapter.PersonField.ADDRESS, emptyAddress));
        assertThrows(InvalidFieldException.class, () ->
                personAdapter.edit(PersonAdapter.PersonField.ADDRESS, spacesOnly));
    }

    @Test
    public void editPersonTags_changesSubject() throws InvalidFieldException {
        personAdapter.edit(PersonAdapter.PersonField.TAGS, "friends colleagues");
        Set<Tag> modifiedTags = new HashSet<>(Arrays.asList(new Tag("friends"), new Tag("colleagues")));

        assertEquals(new Person(ALICE.getName(), ALICE.getPhone(), ALICE.getEmail(),
                ALICE.getAddress(), modifiedTags), personAdapter.getSubject());
        assertNotEquals(ALICE, personAdapter.getSubject());

        personAdapter.edit(PersonAdapter.PersonField.TAGS, "friends");
        assertEquals(ALICE, personAdapter.getSubject());

    }
}
