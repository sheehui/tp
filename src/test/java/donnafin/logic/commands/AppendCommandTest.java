package donnafin.logic.commands;

import static donnafin.testutil.Assert.assertThrows;
import static donnafin.testutil.TypicalPersons.GEORGE;
import static donnafin.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import donnafin.commons.exceptions.IllegalValueException;
import donnafin.logic.PersonAdapter;
import donnafin.logic.commands.exceptions.CommandException;
import donnafin.model.AddressBook;
import donnafin.model.Model;
import donnafin.model.ModelManager;
import donnafin.model.UserPrefs;
import donnafin.model.person.Asset;
import donnafin.model.person.Liability;
import donnafin.model.person.Person;
import donnafin.model.person.Policy;
import donnafin.storage.JsonAddressBookStorage;
import donnafin.storage.JsonUserPrefsStorage;
import donnafin.storage.StorageManager;

public class AppendCommandTest {
    private static Asset testAsset;
    private static Liability testLiability;
    private static Policy testPolicy;

    private static Set<Asset> combinedAssets;
    private static Set<Liability> combinedLiability;
    private static Set<Policy> combinedPolicy;

    @TempDir
    public Path temporaryFolder;
    private PersonAdapter personAdapter;

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), null);

    @BeforeEach
    public void setUp() throws IllegalValueException {
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        Model model = new ModelManager(new AddressBook(), new UserPrefs(), storage);
        model.addPerson(GEORGE);
        personAdapter = new PersonAdapter(model, GEORGE);

        testPolicy = new Policy("Golden Mile", "AIA",
                "$2000", "$100", "$200");
        testLiability = new Liability("Mortgage debt", "Debt", "$20000",
                "23 year loan from DBS Bank.");
        testAsset = new Asset("Good Class Bungalow", "Property",
                "$2000000", "Paid in full. No debt.");

        combinedPolicy = new HashSet<>(GEORGE.getPolicies());
        combinedPolicy.add(testPolicy);
        combinedLiability = new HashSet<>(GEORGE.getLiabilities());
        combinedLiability.add(testLiability);
        combinedAssets = new HashSet<>(GEORGE.getAssets());
        combinedAssets.add(testAsset);

    }

    @Test
    public void appendCommandEqualsTest() {
        AppendCommand testCommand = new AppendCommand(personAdapter, testPolicy);
        AppendCommand testCommand2 = new AppendCommand(personAdapter, testLiability);

        // same object -> returns true
        assertTrue(testCommand.equals(testCommand));

        // not instance of AppendCommand -> returns false
        assertFalse(testCommand.equals(null));

        // different AppendCommand -> returns false
        assertFalse(testCommand.equals(testCommand2));
    }

    @Test
    public void appendNewPolicy_changesPolicyList() throws CommandException {
        AppendCommand testCommand = new AppendCommand(personAdapter, testPolicy);
        testCommand.execute(model);

        assertEquals(new Person(GEORGE.getName(), GEORGE.getPhone(), GEORGE.getEmail(), GEORGE.getAddress(),
                GEORGE.getNotes(), combinedPolicy, GEORGE.getLiabilities(),
                GEORGE.getAssets()), personAdapter.getSubject());

        assertNotEquals(GEORGE, personAdapter.getSubject());
    }

    @Test
    public void appendNewLiability_changesLiabilityList() throws CommandException {
        AppendCommand testCommand = new AppendCommand(personAdapter, testLiability);
        testCommand.execute(model);

        assertEquals(new Person(GEORGE.getName(), GEORGE.getPhone(), GEORGE.getEmail(), GEORGE.getAddress(),
                GEORGE.getNotes(), GEORGE.getPolicies(), combinedLiability,
                GEORGE.getAssets()), personAdapter.getSubject());

        assertNotEquals(GEORGE, personAdapter.getSubject());
    }

    @Test
    public void appendNewAsset_changesAssetList() throws CommandException {
        AppendCommand testCommand = new AppendCommand(personAdapter, testAsset);
        testCommand.execute(model);

        assertEquals(new Person(GEORGE.getName(), GEORGE.getPhone(), GEORGE.getEmail(), GEORGE.getAddress(),
                GEORGE.getNotes(), GEORGE.getPolicies(), GEORGE.getLiabilities(),
                combinedAssets), personAdapter.getSubject());

        assertNotEquals(GEORGE, personAdapter.getSubject());
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        AppendCommand testCommand = new AppendCommand(personAdapter, testAsset);
        assertThrows(NullPointerException.class, () -> testCommand.execute(null));
    }

    @Test
    public void constructor_nullPersonAdapter_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AppendCommand(null, testAsset));
        assertThrows(NullPointerException.class, () -> new AppendCommand(null, testLiability));
        assertThrows(NullPointerException.class, () -> new AppendCommand(null, testPolicy));
    }


}
