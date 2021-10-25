package donnafin.logic;

import static donnafin.testutil.Assert.assertThrows;
import static donnafin.testutil.TypicalPersons.ALICE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import donnafin.model.AddressBook;
import donnafin.model.Model;
import donnafin.model.ModelManager;
import donnafin.model.UserPrefs;
import donnafin.model.person.Address;
import donnafin.model.person.Email;
import donnafin.model.person.Name;
import donnafin.model.person.Notes;
import donnafin.model.person.Person;
import donnafin.model.person.Phone;
import donnafin.model.tag.Tag;
import donnafin.storage.JsonAddressBookStorage;
import donnafin.storage.JsonUserPrefsStorage;
import donnafin.storage.StorageManager;

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
        model.addPerson(ALICE);
        personAdapter = new PersonAdapter(model, ALICE);
    }

    @Test
    public void constructor() {
        assertEquals(ALICE, personAdapter.getSubject());
    }

    @Test
    public void getContactAttributesList_returnsCorrectOutput() {
        assertEquals(ALICE.getContactAttributesList(), personAdapter.getContactAttributesList());
    }

    @Test
    public void editPersonName_changesSubject() throws InvalidFieldException {
        personAdapter.edit(new Name("Peter"));

        assertEquals(new Person(new Name("Peter"), ALICE.getPhone(), ALICE.getEmail(),
                ALICE.getAddress(), ALICE.getTags(), ALICE.getNotes(), ALICE.getPolicies(),
                ALICE.getLiabilities(), ALICE.getAssets()),
                personAdapter.getSubject());
        assertNotEquals(ALICE, personAdapter.getSubject());

        personAdapter.edit(new Name("Alice Pauline"));
        assertEquals(ALICE, personAdapter.getSubject());
    }

    @Test
    public void editPersonName_withIncorrectNameFormat_throwsInvalidFieldException() {
        String emptyName = " "; // empty string
        String invalidName = "Alice*"; // contains non-alphanumeric characters
        assertThrows(IllegalArgumentException.class, () -> personAdapter.edit(new Name(emptyName)));
        assertThrows(IllegalArgumentException.class, () ->
                personAdapter.edit(new Name(invalidName)));
    }

    @Test
    public void editPersonPhone_changesSubject() throws InvalidFieldException {
        personAdapter.edit(new Phone("90538978"));

        assertEquals(new Person(ALICE.getName(), new Phone("90538978"),
                ALICE.getEmail(), ALICE.getAddress(), ALICE.getTags(), ALICE.getNotes(),
                ALICE.getPolicies(), ALICE.getLiabilities(), ALICE.getAssets()),
                personAdapter.getSubject());
        assertNotEquals(ALICE, personAdapter.getSubject());

        personAdapter.edit(ALICE.getPhone());
        assertEquals(ALICE, personAdapter.getSubject());
    }

    @Test
    public void editPersonPhone_withIncorrectPhoneFormat_throwsInvalidFieldException() {
        String emptyPhone = " "; // empty string
        String invalidPhone = "91"; // less than 3 numbers
        String invalidPhoneAlt = "9011p041"; // alphabets within digits
        assertThrows(IllegalArgumentException.class, () ->
                personAdapter.edit(new Phone(emptyPhone)));
        assertThrows(IllegalArgumentException.class, () ->
                personAdapter.edit(new Phone(invalidPhone)));
        assertThrows(IllegalArgumentException.class, () ->
                personAdapter.edit(new Phone(invalidPhoneAlt)));
    }

    @Test
    public void editPersonEmail_changesSubject() throws InvalidFieldException {
        personAdapter.edit(new Email("alice29@email.com"));

        assertEquals(new Person(ALICE.getName(), ALICE.getPhone(), new Email("alice29@email.com"),
                ALICE.getAddress(), ALICE.getTags(), ALICE.getNotes(), ALICE.getPolicies(),
                ALICE.getLiabilities(), ALICE.getAssets()),
                personAdapter.getSubject());
        assertNotEquals(ALICE, personAdapter.getSubject());

        personAdapter.edit(ALICE.getEmail());
        assertEquals(ALICE, personAdapter.getSubject());
    }

    @Test
    public void editPersonEmail_withIncorrectPhoneFormat_throwsInvalidFieldException() {
        String missingLocalPart = "@example.com"; //missing local part
        String missingAtSymbol = "peterjackexample.com"; //missing '@' symbol
        String missingDomainName = "peterjack@"; //missing domain name

        assertThrows(IllegalArgumentException.class, () ->
                personAdapter.edit(new Email(missingLocalPart)));
        assertThrows(IllegalArgumentException.class, () ->
                personAdapter.edit(new Email(missingAtSymbol)));
        assertThrows(IllegalArgumentException.class, () ->
                personAdapter.edit(new Email(missingDomainName)));
    }

    @Test
    public void editPersonAddress_changesSubject() throws InvalidFieldException {
        personAdapter.edit(new Address("28 College Ave E, #B1-01, Singapore 138598"));

        assertEquals(new Person(ALICE.getName(), ALICE.getPhone(), ALICE.getEmail(),
                new Address("28 College Ave E, #B1-01, Singapore 138598"),
                ALICE.getTags(), ALICE.getNotes(), ALICE.getPolicies(), ALICE.getLiabilities(),
                ALICE.getAssets()), personAdapter.getSubject());
        assertNotEquals(ALICE, personAdapter.getSubject());

        personAdapter.edit(ALICE.getAddress());
        assertEquals(ALICE, personAdapter.getSubject());
    }

    @Test
    public void editPersonAddress_withIncorrectPhoneFormat_throwsInvalidFieldException() {
        String emptyAddress = ""; //empty String
        String spacesOnly = " "; //spaces only

        assertThrows(IllegalArgumentException.class, () ->
                personAdapter.edit(new Address(emptyAddress)));
        assertThrows(IllegalArgumentException.class, () ->
                personAdapter.edit(new Address(spacesOnly)));
    }

    @Test
    public void editPersonNotes_changesSubject() throws InvalidFieldException {
        personAdapter.edit(new Notes("Loves cai fan & teh ping"));
        Notes modifiedNotes = new Notes("Loves cai fan & teh ping");

        assertEquals(new Person(ALICE.getName(), ALICE.getPhone(), ALICE.getEmail(),
                ALICE.getAddress(), ALICE.getTags(), modifiedNotes, ALICE.getPolicies(),
                        ALICE.getLiabilities(), ALICE.getAssets()),
                personAdapter.getSubject());
        assertNotEquals(ALICE, personAdapter.getSubject());

        personAdapter.edit(new Notes("Loves cai fan"));
        assertEquals(ALICE, personAdapter.getSubject());
    }
}
