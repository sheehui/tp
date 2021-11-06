package donnafin.logic;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Logger;

import donnafin.commons.core.LogsCenter;
import donnafin.model.Model;
import donnafin.model.person.Address;
import donnafin.model.person.Asset;
import donnafin.model.person.Attribute;
import donnafin.model.person.Email;
import donnafin.model.person.Liability;
import donnafin.model.person.Name;
import donnafin.model.person.Notes;
import donnafin.model.person.Person;
import donnafin.model.person.Phone;
import donnafin.model.person.Policy;
import javafx.collections.ObservableList;

public class PersonAdapter {

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);
    private final Model model;
    private Person subject;

    public enum PersonField {
        NAME,
        PHONE,
        EMAIL,
        ADDRESS,
        NOTES,
        POLICIES,
        LIABILITIES,
        ASSETS
    }

    /**
     * Constructor for the PersonAdapter.
     * @param model the model of the current DonnaFin application.
     * @param subject person that you want to work on.
     */
    public PersonAdapter(Model model, Person subject) {
        this.model = model;
        this.subject = subject;
        logger.fine("PersonAdapter created for " + subject);
    }

    /** Get the immutable Person object. */
    public Person getSubject() {
        return this.subject;
    }

    /**
     * Get all attributes from Person.
     * @return Observable list of attributes
     */
    public ObservableList<Attribute> getContactAttributesList() {
        return subject.getContactAttributesList();
    }

    /**
     * Method to edit the client's name.
     * @param newName new name for the client.
     */
    public void edit(Name newName) {
        Person curr = this.subject;
        Person personToEdit = new Person(
                newName,
                curr.getPhone(),
                curr.getEmail(),
                curr.getAddress(),
                curr.getNotes(),
                curr.getPolicies(),
                curr.getLiabilities(),
                curr.getAssets()
        );
        this.subject = personToEdit;
        this.trySaveAddressBook(curr, personToEdit);
        logger.fine(String.format("Updated name: %s -> %s", curr.getName(), subject.getName()));
    }

    /**
     * Method to edit the client's phone number.
     * @param newPhone new phone number for the client.
     */
    public void edit(Phone newPhone) {
        Person curr = this.subject;
        Person personToEdit = new Person(
                curr.getName(),
                newPhone,
                curr.getEmail(),
                curr.getAddress(),
                curr.getNotes(),
                curr.getPolicies(),
                curr.getLiabilities(),
                curr.getAssets()
        );
        this.subject = personToEdit;
        this.trySaveAddressBook(curr, personToEdit);
        logger.fine(String.format("Updated phone: %s -> %s", curr.getPhone(), subject.getPhone()));
    }

    /**
     * Method to edit the client's email.
     * @param newEmail new email for the client.
     */
    public void edit(Email newEmail) {
        Person curr = this.subject;
        Person personToEdit = new Person(
                curr.getName(),
                curr.getPhone(),
                newEmail,
                curr.getAddress(),
                curr.getNotes(),
                curr.getPolicies(),
                curr.getLiabilities(),
                curr.getAssets()
        );
        this.subject = personToEdit;
        this.trySaveAddressBook(curr, personToEdit);
        logger.fine(String.format("Updated email: %s -> %s", curr.getEmail(), subject.getEmail()));
    }

    /**
     * Method to edit the client's address.
     * @param newAddress new address for the client.
     */
    public void edit(Address newAddress) {
        Person curr = this.subject;
        Person personToEdit = new Person(
                curr.getName(),
                curr.getPhone(),
                curr.getEmail(),
                newAddress,
                curr.getNotes(),
                curr.getPolicies(),
                curr.getLiabilities(),
                curr.getAssets()
        );
        this.subject = personToEdit;
        this.trySaveAddressBook(curr, personToEdit);
        logger.fine(String.format("Updated address: %s -> %s", curr.getAddress(), subject.getAddress()));
    }

    /**
     * Method to edit the client's notes.
     * @param newNotes new notes for the client.
     */
    public void edit(Notes newNotes) {
        Person curr = this.subject;
        Person personToEdit = new Person(
                curr.getName(),
                curr.getPhone(),
                curr.getEmail(),
                curr.getAddress(),
                newNotes,
                curr.getPolicies(),
                curr.getLiabilities(),
                curr.getAssets()
        );
        this.subject = personToEdit;
        this.trySaveAddressBook(curr, personToEdit);
        logger.fine(String.format("Updated notes: %s -> %s", curr.getNotes(), subject.getNotes()));
    }

    /**
     * Method to edit the client's policies.
     * @param newPolicies new policies for the client.
     */
    public void editPolicies(Set<Policy> newPolicies) {
        Person curr = this.subject;
        Person personToEdit = new Person(
                curr.getName(),
                curr.getPhone(),
                curr.getEmail(),
                curr.getAddress(),
                curr.getNotes(),
                newPolicies,
                curr.getLiabilities(),
                curr.getAssets()
        );
        this.subject = personToEdit;
        this.trySaveAddressBook(curr, personToEdit);
        logger.fine(String.format("Updated policies: ... -> %s", subject.getPolicies().stream()
                .map(Policy::toString).reduce("", (a, b) -> a + b)));
    }

    /**
     * Method to edit the client's liabilities.
     * @param newLiabilities new liabilities for the client.
     */
    public void editLiabilities(Set<Liability> newLiabilities) {
        Person curr = this.subject;
        Person personToEdit = new Person(
                curr.getName(),
                curr.getPhone(),
                curr.getEmail(),
                curr.getAddress(),
                curr.getNotes(),
                curr.getPolicies(),
                newLiabilities,
                curr.getAssets()
        );
        this.subject = personToEdit;
        this.trySaveAddressBook(curr, personToEdit);
        logger.fine(String.format("Updated liabilities: ... -> %s", subject.getLiabilities().stream()
                .map(Liability::toString).reduce("", (a, b) -> a + b)));
    }

    /**
     * Method to edit the client's assets.
     * @param newAssets new assets for the client.
     */
    public void editAssets(Set<Asset> newAssets) {
        Person curr = this.subject;
        Person personToEdit = new Person(
                curr.getName(),
                curr.getPhone(),
                curr.getEmail(),
                curr.getAddress(),
                curr.getNotes(),
                curr.getPolicies(),
                curr.getLiabilities(),
                newAssets
        );
        this.subject = personToEdit;
        this.trySaveAddressBook(curr, personToEdit);
        logger.fine(String.format("Updated assets: ... -> %s", subject.getAssets().stream()
                .map(Asset::toString).reduce("", (a, b) -> a + b)));
    }

    private void trySaveAddressBook(Person person, Person newPerson) {
        try {
            model.setPerson(person, newPerson);
            model.saveAddressBook();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
