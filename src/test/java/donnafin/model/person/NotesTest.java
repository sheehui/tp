package donnafin.model.person;

import org.junit.jupiter.api.Test;

import static donnafin.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NotesTest {

    @Test
    public void constructor_validInputs_matchesExpectedString() {
        assertEquals("loves kopi", new Notes("loves kopi").toString());
        assertEquals("loves kopi\n\n\nloves chai", new Notes("loves kopi\n\n\nloves chai").toString());
        assertEquals("loves kopi", new Notes("   loves kopi   ").toString());
        assertEquals("loves kopi\n\n\nloves chai", new Notes("\nloves kopi\n\n\nloves chai\n\n").toString());
        assertEquals("loves kopi\n\n\nloves chai", new Notes(" \n  loves kopi\n\n\nloves chai\n\n   ").toString());
    }

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
        assertFalse(Notes.isValidNotes("    "));

        // valid notes
        assertTrue(Notes.isValidNotes("Joshua is a family-oriented client. Has a great preference for crypto"
                + " in his portfolio."));
    }
}
