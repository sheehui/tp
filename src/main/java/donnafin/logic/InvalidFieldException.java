package donnafin.logic;

/**
 * An error thrown when  the input format is not formatted according to the specifications.
 */
public class InvalidFieldException extends Exception {
    public InvalidFieldException(PersonAdapter.PersonField field) {
        super("The following field: " + field.toString() + " was specified incorrectly");
    }
}
