package donnafin.model.person;

import org.junit.jupiter.api.Test;

import static donnafin.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NotesTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Notes(null));
    }

    @Test
    public void isValidAddress() {
        // null notes
        assertThrows(NullPointerException.class, () -> Notes.isValidNotes(null));

        // invalid notes
        assertFalse(Notes.isValidNotes("    client has bad temper")); // whitespace at the beginning
        assertFalse(Notes.isValidNotes("   very wealthy   ")); // whitespace at the end and beginning
        assertFalse(Notes.isValidNotes("loves ice cream     ")); // whitespace at the end

        // valid notes
        assertTrue(Notes.isValidNotes("Joshua is a family-oriented client. Has a great preference for crypto"
                + " in his portfolio."));
    }
}
