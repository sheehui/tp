package donnafin.logic;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import donnafin.model.Model;
import donnafin.model.person.Address;
import donnafin.model.person.Assets;
import donnafin.model.person.Attribute;
import donnafin.model.person.Commission;
import donnafin.model.person.Email;
import donnafin.model.person.Liabilities;
import donnafin.model.person.Name;
import donnafin.model.person.Notes;
import donnafin.model.person.Person;
import donnafin.model.person.Phone;
import donnafin.model.person.Policy;
import donnafin.model.tag.Tag;
import javafx.collections.ObservableList;

public class PersonAdapter {

    private final Model model;
    private Person subject;

    public enum PersonField {
        NAME,
        PHONE,
        EMAIL,
        ADDRESS,
        TAGS,
        NOTES,
        POLICY,
        LIABILITIES,
        COMMISSION,
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
    }

    /** Get the immutable Person object. */
    public Person getSubject() {
        return this.subject;
    }

    /**
     * Accept parameters to edit the person
     *
     * @param field    to be edited.
     * @param newValue to replace the current value.
     * @return new Person object which is modified if no errors with the new field input.
     * @throws InvalidFieldException if creating a new {@code Person} was not possible with the value.
     */
    public Person edit(PersonField field, String newValue) throws InvalidFieldException {
        try {
            Person curr = this.subject;
            Person newPerson = editPerson(curr, field, newValue);
            this.subject = newPerson;
            model.setPerson(curr, newPerson);
            return newPerson;
        } catch (IllegalArgumentException e) {
            throw new InvalidFieldException(field);
        }
    }

    /**
     * Get all attributes from Person.
     * @return Observable list of attributes
     */
    public ObservableList<Attribute> getAllAttributesList() {
        return subject.getPersonalAttributesList();
    }

    private Person editPerson(Person personToEdit, PersonField field, String newValue) {
        switch (field) {
        case NAME:
            return editPersonName(personToEdit, newValue);
        case PHONE:
            return editPersonPhone(personToEdit, newValue);
        case EMAIL:
            return editPersonEmail(personToEdit, newValue);
        case ADDRESS:
            return editPersonAddress(personToEdit, newValue);
        case TAGS:
            return editPersonTags(personToEdit, newValue);
        case NOTES:
            return editPersonNotes(personToEdit, newValue);
        case POLICY:
            return editPersonPolicies(personToEdit, newValue);
        case LIABILITIES:
            return editPersonLiabilities(personToEdit, newValue);
        case COMMISSION:
            return editPersonCommission(personToEdit, newValue);
        case ASSETS:
            return editPersonAssets(personToEdit, newValue);
        default:
            return personToEdit;
        }
    }

    private Person editPersonName(Person personToEdit, String newValue) {
        Name newName = new Name(newValue);
        return new Person(
                newName, personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(),
                personToEdit.getNotes(), personToEdit.getPolicies(),
                personToEdit.getLiabilities(),  personToEdit.getCommission(),
                personToEdit.getAssetsSet());
    }

    private Person editPersonPhone(Person personToEdit, String newValue) {
        Phone newPhone = new Phone(newValue);
        return new Person(
                personToEdit.getName(), newPhone, personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(), personToEdit.getNotes(),
                personToEdit.getPolicies(), personToEdit.getLiabilities(),
                personToEdit.getCommission(), personToEdit.getAssetsSet());
        );
    }

    private Person editPersonEmail(Person personToEdit, String newValue) {
        Email newEmail = new Email(newValue);
        return new Person(
                personToEdit.getName(), personToEdit.getPhone(), newEmail,
                personToEdit.getAddress(), personToEdit.getTags(), personToEdit.getNotes(),
                personToEdit.getPolicies(), personToEdit.getLiabilities(),
                personToEdit.getCommission(), personToEdit.getAssetsSet());
    }

    private Person editPersonAddress(Person personToEdit, String newValue) {
        Address newAddress = new Address(newValue);
        return new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                newAddress, personToEdit.getTags(), personToEdit.getNotes(), personToEdit.getPolicies(),
                personToEdit.getLiabilities(), personToEdit.getCommission(), personToEdit.getAssetsSet());

    }

    private Person editPersonTags(Person personToEdit, String newValue) {
        String[] tagStrings = newValue.split(" ");
        Set<Tag> newTags = Arrays.stream(tagStrings)
                .map(Tag::new)
                .collect(Collectors.toSet());
        return new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), newTags, personToEdit.getNotes(),
                personToEdit.getPolicies(),
                personToEdit.getLiabilities(), personToEdit.getCommission(),
                personToEdit.getAssetsSet());
    }

    private Person editPersonNotes(Person personToEdit, String newValue) {
        Notes newNotes = new Notes(newValue);
        return new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(), newNotes,
                personToEdit.getPolicies(),
                personToEdit.getLiabilities(), personToEdit.getCommission(),
                personToEdit.getAssetsSet());
    }

    private Person editPersonPolicies(Person personToEdit, String newValue) {
        String[] policiesString = newValue.split(" ");
        Set<Policy> newPolicies = Arrays.stream(policiesString)
                .map(Policy::new)
                .collect(Collectors.toSet());
        return new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(), personToEdit.getNotes(), newPolicies,
                personToEdit.getLiabilities(), personToEdit.getCommission(),
                personToEdit.getAssetsSet());
    }


    private Person editPersonLiabilities(Person personToEdit, String newValue) {
        Liabilities liability = new Liabilities(newValue);
        return new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(), personToEdit.getNotes(),
                personToEdit.getPolicies(),
                liability, personToEdit.getCommission(),
                personToEdit.getAssetsSet());
    }

    private Person editPersonCommission(Person personToEdit, String newValue) {
        Commission commission = new Commission(newValue);
        return new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(), personToEdit.getNotes(), personToEdit.getPolicies(),
                personToEdit.getLiabilities(), commission,
                personToEdit.getAssetsSet());
    }

    private Person editPersonAssets(Person personToEdit, String newValue) {
        String[] policiesString = newValue.split(" ");
        Set<Assets> newAssets = Arrays.stream(policiesString)
                .map(Assets::new)
                .collect(Collectors.toSet());
        return new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(), personToEdit.getNotes(), personToEdit.getPolicies(),
                personToEdit.getLiabilities(), personToEdit.getCommission(),
                newAssets);
    }


}
