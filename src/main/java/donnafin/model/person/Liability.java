package donnafin.model.person;

import java.util.Arrays;
import java.util.Objects;

import static donnafin.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Person's liability in DonnaFin.
 */
public class Liability implements Attribute {

    public static final String MESSAGE_CONSTRAINTS = "Insert liability constraint here";
    public static final String VALIDATION_REGEX = "[\\s\\S]*";
    public final String name;
    public final String type;
    public final String value;
    public final String remarks;

    /**
     * Constructs a {@code Liability}.
     *
     * @param name A valid Liability name.
     * @param type A Liability type.
     * @param value A Liability's worth.
     * @param remarks A remark on Liability.
     */
    public Liability(String name, String type, String value, String remarks) {
        requireAllNonNull(name, type, value, remarks);
        this.name = name;
        this.type = type;
        this.value = value;
        this.remarks = remarks;
    }

    /**
     * Constructs a {@code Liability} with an array input.
     *
     * @param details Array containing all fields of new Liability.
     */
    public Liability(String[] details) {
        Arrays.stream(details).map(Objects::requireNonNull);
        this.name = details[0];
        this.type = details[1];
        this.value = details[2];
        this.remarks = details[3];
    }

    /**
     * Returns true if a given string is a valid liability name
     */
    public static boolean isValidLiability(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return "Liability{" +
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

        if (!(o instanceof Liability)) {
            return false;
        }

        Liability liability = (Liability) o;
        return name.equals(liability.name)
                && type.equals(liability.type)
                && value.equals(liability.value)
                && remarks.equals(liability.remarks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, value, remarks);
    }

}
