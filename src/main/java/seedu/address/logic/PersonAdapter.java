package seedu.address.logic;

import seedu.address.model.Model;
import seedu.address.model.person.Person;

public class PersonAdapter {

    private final Model model;
    private Person subject;

    PersonAdapter(Model model, Person subject) {
        this.model = model;
        this.subject = subject;
    }

    /** Get the immutable Person object. */
    public Person getSubject() {
        return this.subject;
    }

    // TODO: use proper types for field and newValue
    // Use an enum for field
    // newValue should be a valid type for the given enum, not sure how, generic?

    /**
     * Accept parameters to edit the Person.
     *
     * @param field to be edited.
     * @param newValue to replace current value.
     * @return new Person object.
     */
    public Person edit(Object field, Object newValue) {
        Person curr = this.subject;
        Person newPerson = editPerson(curr, field, newValue);
        model.setPerson(curr, newPerson);
        this.subject = newPerson;
        return newPerson;
    }

    // TODO use field and newValue properly.
    private Person editPerson(Person personToEdit, Object field, Object newValue) {
        return new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags());
    }
}
