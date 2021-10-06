package seedu.address.logic;

import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

public class PersonAdapter {

    private final Model model;
    private Person subject;

    public enum PersonField {
        NAME,
        PHONE,
        EMAIL,
        ADDRESS,
    }

    PersonAdapter(Model model, Person subject) {
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
     * @param field to be edited.
     * @param newValue to replace the current value.
     * @return new Person object which is modified if no errors with the new field input.
     */
    public Person edit(PersonField field, String newValue) {
        Person curr = this.subject;
        Person newPerson = editPerson(curr, field, newValue);
        this.subject = newPerson;
        return newPerson;
    }

    //TODO add edit field for tags.
    private Person editPerson(Person personToEdit, PersonField field, String newValue) {
        switch(field) {
        case NAME:
            return editPersonName(personToEdit, newValue);
        case PHONE:
            return editPersonPhone(personToEdit, newValue);
        case EMAIL:
            return editPersonEmail(personToEdit, newValue);
        case ADDRESS:
            return editPersonAddress(personToEdit, newValue);
        default:
            return personToEdit;
        }
    }

    private Person editPersonName(Person personToEdit, String newValue) {
        try {
            Name newName = new Name(newValue);
            return new Person(
                    newName, personToEdit.getPhone(), personToEdit.getEmail(),
                    personToEdit.getAddress(), personToEdit.getTags());
        } catch (Exception e) {
            e.printStackTrace();
            return personToEdit;
        }
    }

    private Person editPersonPhone(Person personToEdit, String newValue) {
        try {
            Phone newPhone = new Phone(newValue);
            return new Person(
                    personToEdit.getName(), newPhone, personToEdit.getEmail(),
                    personToEdit.getAddress(), personToEdit.getTags());
        } catch (Exception e) {
            e.printStackTrace();
            return personToEdit;
        }
    }

    private Person editPersonEmail(Person personToEdit, String newValue) {
        try {
            Email newEmail = new Email(newValue);
            return new Person(
                    personToEdit.getName(), personToEdit.getPhone(), newEmail,
                    personToEdit.getAddress(), personToEdit.getTags());
        } catch (Exception e) {
            e.printStackTrace();
            return personToEdit;
        }
    }

    private Person editPersonAddress(Person personToEdit, String newValue) {
        try {
            Address newAddress = new Address(newValue);
            return new Person(
                    personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                    newAddress, personToEdit.getTags());
        } catch (Exception e) {
            e.printStackTrace();
            return personToEdit;
        }
    }

}
