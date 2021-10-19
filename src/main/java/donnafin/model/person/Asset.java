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
    public final String name;
    public final String type;
    public final Money value;
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
