package donnafin.logic.commands;

import static donnafin.logic.commands.CommandTestUtil.assertCommandFailure;
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

import donnafin.commons.core.types.Index;
import donnafin.commons.exceptions.IllegalValueException;
import donnafin.logic.PersonAdapter;
import donnafin.logic.commands.exceptions.CommandException;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.model.AddressBook;
import donnafin.model.Model;
import donnafin.model.ModelManager;
import donnafin.model.UserPrefs;
import donnafin.model.person.Asset;
import donnafin.model.person.Liability;
import donnafin.model.person.Policy;
import donnafin.storage.JsonAddressBookStorage;
import donnafin.storage.JsonUserPrefsStorage;
import donnafin.storage.StorageManager;

public class RemoveCommandTest {
    private static Asset testAsset;
    private static Liability testLiability;
    private static Policy testPolicy;

    private static Set<Asset> combinedAssets;
    private static Set<Liability> combinedLiability;
    private static Set<Policy> combinedPolicy;

    @TempDir
    public Path temporaryFolder;
    private PersonAdapter personAdapter;

    private final Index index1 = Index.fromOneBased(1);
    private final Index index2 = Index.fromOneBased(2);

    private final PersonAdapter.PersonField fieldAsset = PersonAdapter.PersonField.ASSETS;
    private final PersonAdapter.PersonField fieldLiability = PersonAdapter.PersonField.LIABILITIES;
    private final PersonAdapter.PersonField fieldPolicy = PersonAdapter.PersonField.POLICIES;
    private final PersonAdapter.PersonField invalidField = PersonAdapter.PersonField.NAME;

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
        testAsset = new Asset("Good Class Bungalow", "Property", "$2000000",
                "Paid in full. No debt.");

        combinedPolicy = new HashSet<>(GEORGE.getPolicies());
        combinedPolicy.add(testPolicy);
        combinedLiability = new HashSet<>(GEORGE.getLiabilities());
        combinedLiability.add(testLiability);
        combinedAssets = new HashSet<>(GEORGE.getAssets());
        combinedAssets.add(testAsset);

    }

    @Test
    public void removeCommandEqualsTest() throws ParseException {
        RemoveCommand testCommand = new RemoveCommand(personAdapter, fieldAsset, index1);
        RemoveCommand testCommandAgain = new RemoveCommand(personAdapter, fieldAsset, index1);
        RemoveCommand testCommand2 = new RemoveCommand(personAdapter, fieldLiability, index1);

        // same command -> true
        assertTrue(testCommand.equals(testCommand));
        assertTrue(testCommand.equals(testCommandAgain));

        // null -> false
        assertFalse(testCommand.equals(null));

        // different Remove Command -> false
        assertFalse(testCommand.equals(testCommand2));
    }

    @Test
    public void equals_withMatchingObjects_pass() throws ParseException {
        RemoveCommand testCommand = new RemoveCommand(personAdapter, fieldAsset, index1);
        RemoveCommand testCommandAgain = new RemoveCommand(personAdapter, fieldAsset, index1);
        assertEquals(testCommand, testCommandAgain);
    }

    @Test
    public void hashCode_withMatchingObjects_bothMatch() throws ParseException {
        RemoveCommand testCommand = new RemoveCommand(personAdapter, fieldAsset, index1);
        RemoveCommand testCommandAgain = new RemoveCommand(personAdapter, fieldAsset, index1);
        assertEquals(testCommand.hashCode(), testCommandAgain.hashCode());
    }

    @Test
    public void removeAsset_changesAssetList() throws CommandException, ParseException {
        AppendCommand helperCommand = new AppendCommand(personAdapter, testAsset);
        RemoveCommand testCommand = new RemoveCommand(personAdapter, fieldAsset, index1);
        helperCommand.execute(model);

        assertNotEquals(GEORGE, personAdapter.getSubject());
        testCommand.execute(model);
        assertEquals(GEORGE, personAdapter.getSubject());
    }

    @Test
    public void removeLiability_changesLiabilityList() throws CommandException, ParseException {
        AppendCommand helperCommand = new AppendCommand(personAdapter, testLiability);
        RemoveCommand testCommand = new RemoveCommand(personAdapter, fieldLiability, index2);
        helperCommand.execute(model);

        assertNotEquals(GEORGE, personAdapter.getSubject());
        testCommand.execute(model);
        assertEquals(GEORGE, personAdapter.getSubject());
    }

    @Test
    public void removePolicy_changesPolicyList() throws CommandException, ParseException {
        AppendCommand helperCommand = new AppendCommand(personAdapter, testPolicy);
        RemoveCommand testCommand = new RemoveCommand(personAdapter, fieldPolicy, index1);
        helperCommand.execute(model);

        assertNotEquals(GEORGE, personAdapter.getSubject());
        testCommand.execute(model);
        assertEquals(GEORGE, personAdapter.getSubject());
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() throws ParseException {
        RemoveCommand testCommand = new RemoveCommand(personAdapter, fieldLiability, index2);
        assertCommandFailure(testCommand, model, "No such index found.");
    }

    @Test
    public void execute_invalidTab_throwsCommandException() throws ParseException {
        RemoveCommand testCommand = new RemoveCommand(personAdapter, invalidField, index2);
        assertCommandFailure(testCommand, model, RemoveCommand.MESSAGE_INVALID_TAB);
    }
}
