package donnafin.logic;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

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
    private static final String ATTRIBUTE_DELIMITER = "^]";
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

    /**
     * Accept parameters to edit the person
     *
     * @param field    to be edited.
     * @param newValue to replace the current value.
     * @throws InvalidFieldException if creating a new {@code Person} was not possible with the value.
     */
    public void edit(PersonField field, String newValue) throws InvalidFieldException {
        try {
            Person curr = this.subject;
            Person newPerson = editPerson(curr, field, newValue);
            this.subject = newPerson;
            model.setPerson(curr, newPerson);
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
        case POLICIES:
            return editPersonPolicies(personToEdit, newValue);
        case LIABILITIES:
            return editPersonLiabilities(personToEdit, newValue);
        case ASSETS:
            return editPersonAssets(personToEdit, newValue);
        default:
            return personToEdit;
        }
    }

    private Person editPersonName(Person personToEdit, String newValue) {
        Name newName = new Name(newValue);

        return new Person(
                newName,
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getTags(),
                personToEdit.getNotes(),
                personToEdit.getPolicies(),
                personToEdit.getLiabilities(),
                personToEdit.getAssets());
    }

    private Person editPersonPhone(Person personToEdit, String newValue) {
        Phone newPhone = new Phone(newValue);

        return new Person(
                personToEdit.getName(),
                newPhone,
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getTags(),
                personToEdit.getNotes(),
                personToEdit.getPolicies(),
                personToEdit.getLiabilities(),
                personToEdit.getAssets()
        );
    }

    private Person editPersonEmail(Person personToEdit, String newValue) {
        Email newEmail = new Email(newValue);

        return new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                newEmail,
                personToEdit.getAddress(),
                personToEdit.getTags(),
                personToEdit.getNotes(),
                personToEdit.getPolicies(),
                personToEdit.getLiabilities(),
                personToEdit.getAssets());
    }

    private Person editPersonAddress(Person personToEdit, String newValue) {
        Address newAddress = new Address(newValue);

        return new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                newAddress,
                personToEdit.getTags(),
                personToEdit.getNotes(),
                personToEdit.getPolicies(),
                personToEdit.getLiabilities(),
                personToEdit.getAssets());

    }

    private Person editPersonTags(Person personToEdit, String newValue) {
        String[] tagStrings = newValue.split(" ");
        Set<Tag> newTags = Arrays.stream(tagStrings)
                .map(Tag::new)
                .collect(Collectors.toSet());

        return new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                newTags,
                personToEdit.getNotes(),
                personToEdit.getPolicies(),
                personToEdit.getLiabilities(),
                personToEdit.getAssets());
    }

    private Person editPersonNotes(Person personToEdit, String newValue) {
        Notes newNotes = new Notes(newValue);

        return new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getTags(),
                newNotes,
                personToEdit.getPolicies(),
                personToEdit.getLiabilities(),
                personToEdit.getAssets());
    }

    private Policy stringToPolicy(String policyString) {
        String[] details = policyString.split(ATTRIBUTE_DELIMITER);
        //@parserteam do error handling for this
        //        if (details != 5) {
        //            throw new ParseException(Policy.MESSAGE_CONSTRAINTS);
        //        }

        return new Policy(details);
    }

    private Person editPersonPolicies(Person personToEdit, String newValue) {
        String[] policiesStrings = newValue.split(SET_DELIMITER);
        Set<Policy> newPolicies = Arrays.stream(policiesStrings)
                .map(this::stringToPolicy)
                .collect(Collectors.toSet());

        return new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getTags(),
                personToEdit.getNotes(),
                newPolicies,
                personToEdit.getLiabilities(),
                personToEdit.getAssets());
    }

    private Liability stringToLiability(String liabilityString) {
        String[] details = liabilityString.split(ATTRIBUTE_DELIMITER);
        //@parserteam do error handling for this
        //        if (details != 4) {
        //            throw new ParseException(Liability.MESSAGE_CONSTRAINTS);
        //        }

        return new Liability(details);
    }

    private Person editPersonLiabilities(Person personToEdit, String newValue) {
        String[] liabilitiesStrings = newValue.split(ATTRIBUTE_DELIMITER);
        Set<Liability> newLiabilities = Arrays.stream(liabilitiesStrings)
                .map(this::stringToLiability)
                .collect(Collectors.toSet());

        return new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getTags(),
                personToEdit.getNotes(),
                personToEdit.getPolicies(),
                newLiabilities,
                personToEdit.getAssets());
    }

    private Asset stringToAsset(String assetString) {
        String[] details = assetString.split(ATTRIBUTE_DELIMITER);
        //@parserteam do error handling for this
        //        if (details != 4) {
        //            throw new ParseException(Asset.MESSAGE_CONSTRAINTS);
        //        }

        return new Asset(details);
    }

    private Person editPersonAssets(Person personToEdit, String newValue) {
        String[] assetsStrings = newValue.split(SET_DELIMITER);
        Set<Asset> newAssets = Arrays.stream(assetsStrings)
                .map(this::stringToAsset)
                .collect(Collectors.toSet());

        return new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getTags(),
                personToEdit.getNotes(),
                personToEdit.getPolicies(),
                personToEdit.getLiabilities(),
                newAssets);
    }

}
