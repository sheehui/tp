package donnafin.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents notes that are associated with the person in the address book.
 *
 */
public class Notes implements Attribute {

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].+[^\\s]";

    public final String notes;

    /**
     * Constructs a note.
     * @param notes the note associated with the person.
     */
    public Notes(String notes) {
        requireNonNull(notes);
        this.notes = notes.trim();
    }

    public String getNotes() {
        return notes;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidNotes(String test) {
        return test.matches(VALIDATION_REGEX);
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

    @Override
    public boolean isPossibleDuplicate(Attribute other) {
        return equals(other);
    }
}
