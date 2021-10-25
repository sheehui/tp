package donnafin.logic;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import donnafin.logic.parser.ParserUtil;
import donnafin.logic.parser.exceptions.ParseException;
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
import donnafin.model.tag.Tag;
import javafx.collections.ObservableList;

public class PersonAdapter {

    private static final String SET_DELIMITER = "<>";
    private static final String ATTRIBUTE_DELIMITER = ";;;";
    private final Model model;
    private Person subject;

    public enum PersonField {
        NAME,
        PHONE,
        EMAIL,
        ADDRESS,
        TAGS,
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
    }

    /** Get the immutable Person object. */
    public Person getSubject() {
        return this.subject;
    }

//    /**
//     * Accept parameters to edit the person
//     *
//     * @param field    to be edited.
//     * @param newValue to replace the current value.
//     * @throws InvalidFieldException if creating a new {@code Person} was not possible with the value.
//     */
//    public void edit(PersonField field, String newValue) throws InvalidFieldException {
//        try {
//            Person curr = this.subject;
//            Person newPerson = editPerson(curr, field, newValue);
//            this.subject = newPerson;
//            model.setPerson(curr, newPerson);
//        } catch (IllegalArgumentException | ParseException e) {
//            throw new InvalidFieldException(field);
//        }
//    }
//
    /**
     * Get all attributes from Person.
     * @return Observable list of attributes
     */
    public ObservableList<Attribute> getContactAttributesList() {
        return subject.getContactAttributesList();
    }
//
//    private Person editPerson(Person personToEdit, PersonField field, String newValue) throws ParseException {
//        switch (field) {
//        case NAME:
//            return editPersonName(personToEdit, newValue);
//        case PHONE:
//            return editPersonPhone(personToEdit, newValue);
//        case EMAIL:
//            return editPersonEmail(personToEdit, newValue);
//        case ADDRESS:
//            return editPersonAddress(personToEdit, newValue);
//        case TAGS:
//            return editPersonTags(personToEdit, newValue);
//        case NOTES:
//            return editPersonNotes(personToEdit, newValue);
//        case POLICIES:
//            return editPersonPolicies(personToEdit, newValue);
//        case LIABILITIES:
//            return editPersonLiabilities(personToEdit, newValue);
//        case ASSETS:
//            return editPersonAssets(personToEdit, newValue);
//        default:
//            return personToEdit;
//        }
//    }

    public void edit(Name newName) {
        Person curr = this.subject;
        Person personToEdit = new Person(
                newName,
                curr.getPhone(),
                curr.getEmail(),
                curr.getAddress(),
                curr.getTags(),
                curr.getNotes(),
                curr.getPolicies(),
                curr.getLiabilities(),
                curr.getAssets()
        );
        this.subject = personToEdit;
        model.setPerson(curr, personToEdit);
    }

    public void edit(Phone newPhone) {
        Person curr = this.subject;
        Person personToEdit = new Person(
                curr.getName(),
                newPhone,
                curr.getEmail(),
                curr.getAddress(),
                curr.getTags(),
                curr.getNotes(),
                curr.getPolicies(),
                curr.getLiabilities(),
                curr.getAssets()
        );
        this.subject = personToEdit;
        model.setPerson(curr, personToEdit);
    }

    public void edit(Email newEmail) {
        Person curr = this.subject;
        Person personToEdit = new Person(
                curr.getName(),
                curr.getPhone(),
                newEmail,
                curr.getAddress(),
                curr.getTags(),
                curr.getNotes(),
                curr.getPolicies(),
                curr.getLiabilities(),
                curr.getAssets()
        );
        this.subject = personToEdit;
        model.setPerson(curr, personToEdit);
    }

    public void edit(Address newAddress) {
        Person curr = this.subject;
        Person personToEdit = new Person(
                curr.getName(),
                curr.getPhone(),
                curr.getEmail(),
                newAddress,
                curr.getTags(),
                curr.getNotes(),
                curr.getPolicies(),
                curr.getLiabilities(),
                curr.getAssets()
        );
        this.subject = personToEdit;
        model.setPerson(curr, personToEdit);
    }

    public void edit(Notes newNotes) {
        Person curr = this.subject;
        Person personToEdit = new Person(
                curr.getName(),
                curr.getPhone(),
                curr.getEmail(),
                curr.getAddress(),
                curr.getTags(),
                newNotes,
                curr.getPolicies(),
                curr.getLiabilities(),
                curr.getAssets()
        );
        this.subject = personToEdit;
        model.setPerson(curr, personToEdit);
    }

    public void editPolicies(Set<Policy> newPolicies) {
        Person curr = this.subject;
        Person personToEdit = new Person(
                curr.getName(),
                curr.getPhone(),
                curr.getEmail(),
                curr.getAddress(),
                curr.getTags(),
                curr.getNotes(),
                newPolicies,
                curr.getLiabilities(),
                curr.getAssets()
        );
        this.subject = personToEdit;
        model.setPerson(curr, personToEdit);
    }

    public void editLiabilities(Set<Liability> newLiabilities) {
        Person curr = this.subject;
        Person personToEdit = new Person(
                curr.getName(),
                curr.getPhone(),
                curr.getEmail(),
                curr.getAddress(),
                curr.getTags(),
                curr.getNotes(),
                curr.getPolicies(),
                newLiabilities,
                curr.getAssets()
        );
        this.subject = personToEdit;
        model.setPerson(curr, personToEdit);
    }

    public void editAssets(Set<Asset> newAssets) {
        Person curr = this.subject;
        Person personToEdit = new Person(
                curr.getName(),
                curr.getPhone(),
                curr.getEmail(),
                curr.getAddress(),
                curr.getTags(),
                curr.getNotes(),
                curr.getPolicies(),
                curr.getLiabilities(),
                newAssets
        );
        this.subject = personToEdit;
        model.setPerson(curr, personToEdit);
    }

}
