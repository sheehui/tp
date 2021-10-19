package donnafin.model.person;

import static donnafin.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import donnafin.commons.core.types.Money;
import donnafin.logic.parser.ParserUtil;
import donnafin.logic.parser.exceptions.ParseException;

/**
 * Represents a Person's liability in DonnaFin.
 */
public class Liability implements Attribute {

    public static final String MESSAGE_CONSTRAINTS = "Insert liability constraint here";
    public final String name;
    public final String type;
    public final Money value;
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
        try {
            this.value = ParserUtil.parseMoney(value);
        } catch (ParseException e) {
            throw new IllegalArgumentException(Policy.MESSAGE_CONSTRAINTS);
        }
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "Liability{"
                + "name='" + name + '\''
                + ", type='" + type + '\''
                + ", value='" + value + '\''
                + ", remarks='" + remarks + '\''
                + '}';
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
