package donnafin.model.person;

import java.util.Arrays;
import java.util.Objects;

import static donnafin.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Person's assets in DonnaFin.
 */
public class Asset implements Attribute {

    public static final String MESSAGE_CONSTRAINTS = "Insert asset constraint here";
    public static final String VALIDATION_REGEX = "[\\s\\S]*";
    public final String name;
    public final String type;
    public final String value;
    public final String remarks;

    /**
     * Constructs a {@code Asset}.
     *
     * @param name A valid Asset name.
     * @param type An Asset type.
     * @param value An Asset's worth.
     * @param remarks A remark on Asset.
     */
    public Asset(String name, String type, String value, String remarks) {
        requireAllNonNull(name, type, value, remarks);
        this.name = name;
        this.type = type;
        this.value = value;
        this.remarks = remarks;
    }

    /**
     * Constructs a {@code Asset} with an array input.
     *
     * @param details Array containing all fields of new Asset.
     */
    public Asset(String[] details) {
        Arrays.stream(details).map(Objects::requireNonNull);
        this.name = details[0];
        this.type = details[1];
        this.value = details[2];
        this.remarks = details[3];
    }

    /**
     * Returns true if a given string is a valid asset name
     */
    public static boolean isValidAsset(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return "Asset{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", value='" + value + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Asset)) {
            return false;
        }

        Asset asset = (Asset) o;
        return name.equals(asset.name)
                && type.equals(asset.type)
                && value.equals(asset.value)
                && remarks.equals(asset.remarks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, value, remarks);
    }

}
