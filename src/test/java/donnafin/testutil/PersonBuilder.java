package donnafin.testutil;

import java.util.HashSet;
import java.util.Set;

import donnafin.model.person.Address;
import donnafin.model.person.Asset;
import donnafin.model.person.Commission;
import donnafin.model.person.Email;
import donnafin.model.person.Liability;
import donnafin.model.person.Name;
import donnafin.model.person.Notes;
import donnafin.model.person.Person;
import donnafin.model.person.Phone;
import donnafin.model.person.Policy;
import donnafin.model.tag.Tag;
import donnafin.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_NOTES = "";
    public static final String DEFAULT_LIABILITIES = "INSERT LIABILITY HERE";
    public static final String DEFAULT_COMMISSION = "INSERT COMMISSION HERE";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private Notes notes;
    private Set<Policy> policies;
    private Commission commission;
    private Liability liabilities;
    private Set<Asset> assetSet;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        policies = new HashSet<>();
        commission = new Commission(DEFAULT_COMMISSION);
        liabilities = new Liability(DEFAULT_LIABILITIES);
        assetSet = new HashSet<>();
        notes = new Notes(DEFAULT_NOTES);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());
        notes = personToCopy.getNotes();
        policies = new HashSet<>(personToCopy.getPolicies());
        liabilities = personToCopy.getLiability();
        commission = personToCopy.getCommission();
        assetSet = new HashSet<>(personToCopy.getAssetSet());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /** Sets the {@code Notes} of the {@code Person} that we are building. */
    public PersonBuilder withNotes(String notes) {
        this.notes = new Notes(notes);
        return this;
    }

    /**
     * Sets the {@code liabilities} of the {@code Person} that we are building.
     */
    public PersonBuilder withLiability(String liability) {
        this.liabilities = new Liability(liability);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withPolicies(String ... policies) {
        this.policies = SampleDataUtil.getPolicies(policies);
        return this;
    }

    /**
     * Parses the {@code assets} into a {@code Set<assets>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withAssets(String ... assets) {
        this.assetSet = SampleDataUtil.getAssets(assets);
        return this;
    }

    /**
     * Sets the {@code Commission} of the {@code Person} that we are building.
     */
    public PersonBuilder withCommission(String commission) {
        this.commission = new Commission(commission);
        return this;
    }

    /**
     * Creates the person name Amy with the appropriate fields
     * @return a fully constructed Person object.
     */
    public Person build() {
        return new Person(name, phone, email, address, tags,
                notes, policies, liabilities, commission, assetSet);
    }

}
