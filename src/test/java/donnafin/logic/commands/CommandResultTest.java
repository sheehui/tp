package donnafin.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same user feedback -> returns true (uiAction cannot be compared)
        assertEquals(commandResult, new CommandResult("feedback"));

        // same object -> returns true
        assertEquals(commandResult, commandResult);

        // null -> returns false
        assertNotEquals(null, commandResult);

        // different types -> returns false
        assertNotEquals(0.5f, commandResult);

        // different feedbackToUser value -> returns false
        assertNotEquals(commandResult, new CommandResult("different"));

        // note that comparing CommandResult on the basis of their exit / show help is no longer possible.
        // see CommandResult::hashcode and CommandResult::equals
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // note that comparing CommandResult on the basis of their exit / show help is no longer possible.
        // see CommandResult::hashcode and CommandResult::equals
    }
}
