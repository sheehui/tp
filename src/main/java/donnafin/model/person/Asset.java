package donnafin.model.person;

import static donnafin.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import donnafin.commons.core.types.Money;
import donnafin.logic.parser.ParserUtil;
import donnafin.logic.parser.exceptions.ParseException;

/**
 * Represents a Person's assets in DonnaFin.
 */
public class Asset implements Attribute {

    public static final String MESSAGE_CONSTRAINTS = "Insert asset constraint here";
    public static final String VALIDATION_REGEX = "[\\s\\S]*";
    private final String name;
    private final String type;
    private final Money value;
    private final String remarks;

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
        try {
            this.value = ParserUtil.parseMoney(value);
        } catch (ParseException e) {
            throw new IllegalArgumentException(Policy.MESSAGE_CONSTRAINTS);
        }
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "Asset{"
                + "name='" + getName() + '\''
                + ", type='" + getType() + '\''
                + ", value='" + getValue() + '\''
                + ", remarks='" + getRemarks() + '\''
                + '}';
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
        return getName().equals(asset.getName())
                && getType().equals(asset.getType())
                && getValue().equals(asset.getValue())
                && getRemarks().equals(asset.getRemarks());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getType(), getValue(), getRemarks());
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Money getValue() {
        return value;
    }

    public String getValueToString() {
        return value.toString();
    }

    public String getRemarks() {
        return remarks;
    }
}
