package donnafin.model.person;

/**
 * Represents notes that are associated with the person in the address book.
 *
 */
public class Notes implements Attribute {

    public String notes;

    /**
     * Constructs a note.
     * @param note the note associated with the person.
     */
    public Notes(String note) {
        this.notes = note;
    }

    @Override
    public String toString() {
        return notes;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Notes // instanceof handles nulls
                && notes.equals(((Notes) other).notes)); // state check
    }

    @Override
    public int hashCode() {
        return notes.hashCode();
    }
}
