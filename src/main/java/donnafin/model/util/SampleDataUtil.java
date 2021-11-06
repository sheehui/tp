package donnafin.model.util;

import java.util.Set;

import donnafin.model.AddressBook;
import donnafin.model.ReadOnlyAddressBook;
import donnafin.model.person.Address;
import donnafin.model.person.Asset;
import donnafin.model.person.Email;
import donnafin.model.person.Liability;
import donnafin.model.person.Name;
import donnafin.model.person.Notes;
import donnafin.model.person.Person;
import donnafin.model.person.Phone;
import donnafin.model.person.Policy;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    private static final Set<Policy> POLICIES_STAND_IN_ONE = Set.of(new Policy(
            "Golden Age",
            "AIA",
            "$14000",
            "$28",
            "$4"
    ));
    private static final Set<Policy> POLICIES_STAND_IN_TWO = Set.of(new Policy(
            "Golden Age",
            "Prudential",
            "$100000",
            "$2800",
            "$4000"
    ));
    private static final Set<Asset> ASSETS_STAND_IN_ONE = Set.of(new Asset(
            "HDB @Jurong",
            "Property",
            "$300000",
            "BTO"
    ));
    private static final Set<Asset> ASSETS_STAND_IN_TWO = Set.of(new Asset(
            "GCB @Sentosa Cove",
            "Property",
            "$9000000",
            "Newly renovated"
    ));
    private static final Set<Liability> LIABILITIES_STAND_IN_ONE = Set.of(new Liability(
            "Bank debt",
            "debt",
            "$20000",
            "10% interest"
    ));
    private static final Set<Liability> LIABILITIES_STAND_IN_TWO = Set.of(new Liability(
            "Bank Loan for Condo @Marina",
            "Mortgage",
            "$5000000",
            "To be settled in 5 years"
    ));

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    new Notes("Likes bread"),
                    POLICIES_STAND_IN_ONE,
                    LIABILITIES_STAND_IN_ONE,
                    ASSETS_STAND_IN_ONE),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new Notes("Has 2 children"),
                    POLICIES_STAND_IN_TWO,
                    LIABILITIES_STAND_IN_TWO,
                    ASSETS_STAND_IN_TWO),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new Notes(""),
                    POLICIES_STAND_IN_ONE,
                    LIABILITIES_STAND_IN_TWO,
                    ASSETS_STAND_IN_TWO),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new Notes(""),
                    POLICIES_STAND_IN_ONE,
                    LIABILITIES_STAND_IN_ONE,
                    ASSETS_STAND_IN_ONE),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    new Notes(""),
                    POLICIES_STAND_IN_TWO,
                    LIABILITIES_STAND_IN_ONE,
                    ASSETS_STAND_IN_ONE),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"),
                    new Notes(""),
                    POLICIES_STAND_IN_ONE,
                    LIABILITIES_STAND_IN_ONE,
                    ASSETS_STAND_IN_TWO)
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }
}
