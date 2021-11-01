package donnafin.model.person;

import static donnafin.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Notes notes;

    // Financial Information fields
    private final Set<Policy> policies = new HashSet<>();
    private final Set<Liability> liabilities = new HashSet<>();
    private final Set<Asset> assets = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Notes notes,
                  Set<Policy> policies, Set<Liability> liabilities, Set<Asset> assets) {
        requireAllNonNull(name, phone, email, address, notes, policies, liabilities, assets);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.notes = notes;
        this.policies.addAll(policies);
        this.liabilities.addAll(liabilities);
        this.assets.addAll(assets);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Notes getNotes() {
        return notes;
    }

    /**
     * Returns an immutable set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * Valid for policies, assets and liabilities.
     */
    public Set<Policy> getPolicies() {
        return Collections.unmodifiableSet(policies);
    }

    public Set<Asset> getAssets() {
        return Collections.unmodifiableSet(assets);
    }

    public Set<Liability> getLiabilities() {
        return Collections.unmodifiableSet(liabilities);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    public boolean isPossibleDuplicate(Person other) {
        return name.isPossibleDuplicate(other.name);
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        return equals(other, true)
                && ((Person) other).getLiabilities().equals(getLiabilities())
                && ((Person) other).getPolicies().equals(getPolicies())
                && ((Person) other).getAssets().equals(getAssets());
    }

    /**
     * Checks if two {@code Person} are the same during {@code AddCommand} by checking the common attributes
     * and ignoring the composite financial attributes, which are not present.
     *
     * @param other Object being compared.
     * @param ignoreCompoundAttributes Boolean value to determine if financial attributes should be compared.
     * @return Boolean representative if both objects are equal.
     */
    public boolean equals(Object other, boolean ignoreCompoundAttributes) {
        if (!ignoreCompoundAttributes) {
            return equals(other);
        }

        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getNotes().equals(getNotes());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, notes);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Phone: ")
                .append(getPhone())
                .append("; Email: ")
                .append(getEmail())
                .append("; Address: ")
                .append(getAddress());

        Set<Policy> policies = getPolicies();
        if (!policies.isEmpty()) {
            builder.append("; Policies: ");
            policies.forEach(builder::append);
        }

        Set<Liability> liabilities = getLiabilities();
        if (!liabilities.isEmpty()) {
            builder.append("; Liabilities: ");
            liabilities.forEach(builder::append);
        }

        Set<Asset> assets = getAssets();
        if (!assets.isEmpty()) {
            builder.append("; Assets: ");
            assets.forEach(builder::append);
        }

        return builder.toString();
    }

    public ObservableList<Attribute> getContactAttributesList() {
        ObservableList<Attribute> attributeObservableList = FXCollections.observableArrayList();
        attributeObservableList.add(name);
        attributeObservableList.add(phone);
        attributeObservableList.add(email);
        attributeObservableList.add(address);
        return attributeObservableList;
    }

    public ObservableList<Attribute> getFinancialAttributeList() {
        ObservableList<Attribute> attributeObservableList = FXCollections.observableArrayList();
        attributeObservableList.addAll(policies);
        attributeObservableList.addAll(liabilities);
        attributeObservableList.addAll(assets);
        return attributeObservableList;
    }
}
