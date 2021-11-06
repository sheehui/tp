package donnafin.testutil;

import java.util.HashSet;
import java.util.Set;

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
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_NOTES = "";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Notes notes;
    private Set<Policy> policies;
    private Set<Liability> liabilities;
    private Set<Asset> assets;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        policies = new HashSet<>();
        liabilities = new HashSet<>();
        assets = new HashSet<>();
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
        notes = personToCopy.getNotes();
        policies = new HashSet<>(personToCopy.getPolicies());
        liabilities = new HashSet<>(personToCopy.getLiabilities());
        assets = new HashSet<>(personToCopy.getAssets());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
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
    public PersonBuilder withLiability(Set<Liability> liabilities) {
        this.liabilities = liabilities;
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withPolicies(Set<Policy> policies) {
        this.policies = policies;
        return this;
    }

    /**
     * Parses the {@code assets} into a {@code Set<assets>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withAssets(Set<Asset> assets) {
        this.assets = assets;
        return this;
    }

    /**
     * Creates the person name Amy with the appropriate fields
     * @return a fully constructed Person object.
     */
    public Person build() {
        return new Person(name, phone, email, address, notes, policies, liabilities, assets);
    }

}
