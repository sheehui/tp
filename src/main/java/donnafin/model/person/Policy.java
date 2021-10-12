package donnafin.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's policy in DonnaFin.
 */
public class Policy implements Attribute {

    public static final String MESSAGE_CONSTRAINTS = "Insert policy constraint here";
    public static final String VALIDATION_REGEX = "\\s\\S*";
    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param policyName A valid policy number.
     */
    public Policy(String policyName) {
        requireNonNull(policyName);
        value = policyName;
    }

    /**
     * Returns true if a given string is a valid policy name
     */
    public static boolean isValidPolicyName(String test) {
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
