package donnafin.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's assets in DonnaFin.
 */
public class Asset implements Attribute {

    public static final String MESSAGE_CONSTRAINTS = "Insert asset constraint here";
    public static final String VALIDATION_REGEX = "[\\s\\S]+";
    public final String assetName;

    /**
     * Constructs a {@code Asset}.
     *
     * @param asset A valid asset name.
     */
    public Asset(String asset) {
        requireNonNull(asset);
        assetName = asset;
    }

    /**
     * Returns true if a given string is a valid asset name
     */
    public static boolean isValidAsset(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return assetName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof donnafin.model.person.Phone // instanceof handles nulls
                && assetName.equals(((donnafin.model.person.Phone) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return assetName.hashCode();
    }
}
