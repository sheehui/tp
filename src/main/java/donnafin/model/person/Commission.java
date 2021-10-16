package donnafin.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's commission in DonnaFin.
 */
public class Commission implements Attribute {

    public static final String MESSAGE_CONSTRAINTS = "Insert commission constraint here";
    public static final String VALIDATION_REGEX = "[\\s\\S]*";
    public final String value;

    /**
     * Constructs a {@code Commission}.
     *
     * @param commissionAmt A valid commission number.
     */
    public Commission(String commissionAmt) {
        requireNonNull(commissionAmt);
        value = commissionAmt;
    }

    /**
     * Returns true if a given string is a valid commission name.
     */
    public static boolean isValidCommissionName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof donnafin.model.person.Phone // instanceof handles nulls
                && value.equals(((donnafin.model.person.Phone) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
