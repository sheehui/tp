package donnafin.model.person;

import static donnafin.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import donnafin.model.tag.Tag;
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
    private final Set<Tag> tags = new HashSet<>();
    private final Notes notes;

    // Fin info fields
    private final Set<Policy> policies = new HashSet<>();
    private final Liabilities liabilities;
    private final Commission commission;
    private final Set<Assets> assetsSet = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Notes notes,
                  Set<Policy> policy, Liabilities liabilities,
                  Commission commission, Set<Assets> assetsSet) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.notes = notes;
        this.policies.addAll(policy);
        this.liabilities = liabilities;
        this.commission = commission;
        this.assetsSet.addAll(assetsSet);
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

    public Liabilities getLiabilities() {
        return liabilities;
    }

    public Commission getCommission() {
        return commission;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Set<Policy> getPolicies() {
        return Collections.unmodifiableSet(policies);
    }

    public Set<Assets> getAssetsSet() {
        return Collections.unmodifiableSet(assetsSet);
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

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
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
                && otherPerson.getTags().equals(getTags())
                && otherPerson.getNotes().equals(getNotes());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, notes);
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

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }

        builder.append("; Notes: ").append(getNotes());
        return builder.toString();
    }

    public ObservableList<Attribute> getPersonalAttributesList() {
        ObservableList<Attribute> attributeObservableList = FXCollections.observableArrayList();
        attributeObservableList.add(name);
        attributeObservableList.add(phone);
        attributeObservableList.add(email);
        attributeObservableList.add(address);
        return attributeObservableList;
    }

    public ObservableList<Attribute> getFinancialAttributeList() {
        ObservableList<Attribute> attributeObservableList = FXCollections.observableArrayList();
        attributeObservableList.add(liabilities);
        attributeObservableList.add(commission);
        return attributeObservableList;
    }
}
