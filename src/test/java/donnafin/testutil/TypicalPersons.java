package donnafin.testutil;

import static donnafin.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static donnafin.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static donnafin.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static donnafin.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static donnafin.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static donnafin.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static donnafin.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static donnafin.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import donnafin.model.AddressBook;
import donnafin.model.person.Asset;
import donnafin.model.person.Liability;
import donnafin.model.person.Person;
import donnafin.model.person.Policy;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {


    public static final Set<Policy> POLICIES_INPUT = Set.of(new Policy(
            "XYZ Policy Name",
            "Insurer A",
            "$3",
            "$1",
            "$0.50"
    ));
    public static final Set<Liability> LIABILITIES_INPUT = Set.of(new Liability(
            "Home Mortgage",
            "Bank B",
            "$30",
            "5% per annum"
    ));
    public static final Set<Asset> ASSETS_INPUT = Set.of(new Asset(
            "XYZ Asset Name",
            "Crypto",
            "$3000",
            "HODL"
    ));

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withNotes("Loves chicken rice")
            .withPolicies(POLICIES_INPUT)
            .withLiability(LIABILITIES_INPUT)
            .withAssets(ASSETS_INPUT).build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withPolicies(POLICIES_INPUT)
            .withLiability(LIABILITIES_INPUT)
            .withAssets(ASSETS_INPUT).build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street")
            .withNotes("Loves CS2103T")
            .withPolicies(POLICIES_INPUT)
            .withLiability(LIABILITIES_INPUT)
            .withAssets(ASSETS_INPUT).build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street")
            .withNotes("Tells lame jokes")
            .withPolicies(POLICIES_INPUT)
            .withLiability(LIABILITIES_INPUT)
            .withAssets(ASSETS_INPUT).build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave")
            .withNotes("Vim god")
            .withPolicies(POLICIES_INPUT)
            .withLiability(LIABILITIES_INPUT)
            .withAssets(ASSETS_INPUT).build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo")
            .withNotes("Moustachio")
            .withPolicies(POLICIES_INPUT)
            .withLiability(LIABILITIES_INPUT)
            .withAssets(ASSETS_INPUT).build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street")
            .withNotes("Curious")
            .withPolicies(POLICIES_INPUT)
            .withLiability(LIABILITIES_INPUT)
            .withAssets(ASSETS_INPUT).build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").withNotes("To bee or not to bee")
            .withPolicies(POLICIES_INPUT)
            .withLiability(LIABILITIES_INPUT)
            .withAssets(ASSETS_INPUT).build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").withNotes("Sir stop sir")
            .withPolicies(POLICIES_INPUT)
            .withLiability(LIABILITIES_INPUT)
            .withAssets(ASSETS_INPUT).build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withNotes("Enjoys architecture")
            .withPolicies(Set.of(new Policy("Diamond", "AIA", "$100000", "$20", "$1")))
            .withLiability(Set.of(new Liability("company debt", "debt", "$100000", "downgrade imminent")))
            .withAssets(Set.of(new Asset("bitcoin", "crypto", "$10000000", "mad stonks"))).build();

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
