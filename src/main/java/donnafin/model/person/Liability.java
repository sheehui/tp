//@@author sheehui
package donnafin.model.person;

import static donnafin.commons.util.AppUtil.checkArgument;
import static donnafin.commons.util.CollectionUtil.requireAllNonNull;
import static donnafin.logic.parser.CliSyntax.PREFIX_NAME;
import static donnafin.logic.parser.CliSyntax.PREFIX_REMARKS;
import static donnafin.logic.parser.CliSyntax.PREFIX_TYPE;
import static donnafin.logic.parser.CliSyntax.PREFIX_VALUE;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

import donnafin.commons.core.types.Money;
import donnafin.logic.parser.ParserUtil;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.ui.AttributeTable;

/**
 * Represents a Person's liability in DonnaFin.
 */
public class Liability implements Attribute {

    public static final String MESSAGE_CONSTRAINTS = "Liability must be specified with "
            + "a name (non-empty string) "
            + "a type (non-empty string) "
            + "a value (positive monetary value) "
            + "a remark (non-empty string) "
            + "\nE.g. "
            + PREFIX_NAME + "DBS loan for Dempsey Rd unit "
            + PREFIX_TYPE + "Property Mortgage "
            + PREFIX_VALUE + "$420 "
            + PREFIX_REMARKS + "mostly paid off ";
    public static final String VALIDATION_REGEX = "[^\\s].*";
    //@@author bharathcs
    public static final AttributeTable.TableConfig<Liability> TABLE_CONFIG = new AttributeTable.TableConfig<>(
        "Liabilities",
        List.of(
                new AttributeTable.ColumnConfig("Liability Name", "name", 300, 500),
                new AttributeTable.ColumnConfig("Type", "type", 100, 250),
                new AttributeTable.ColumnConfig("Value", "valueToString", 100, 250),
                new AttributeTable.ColumnConfig("Remarks", "remarks", 100, 250)
        ),
        liabilityCol -> liabilityCol.stream()
            .map(Liability::getValue)
            .map(Money::getValue)
            .map(BigInteger::valueOf)
            .reduce(BigInteger::add)
            .map(i -> {
                BigInteger unit = BigInteger.valueOf(100);
                String cents = i.mod(unit).add(unit).toString().substring(1);
                String dollars = i.divide(unit).toString();
                return String.format("Total Liability Value: $%s.%s", dollars, cents);
            }).orElse("")
    );
    //@@author sheehui

    private final String name;
    private final String type;
    private final Money value;
    private final String remarks;

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
        checkArgument(isValidVariable(name), MESSAGE_CONSTRAINTS);
        checkArgument(isValidVariable(type), MESSAGE_CONSTRAINTS);
        checkArgument(isValidVariable(remarks), MESSAGE_CONSTRAINTS);
        this.name = name;
        this.type = type;
        try {
            this.value = ParserUtil.parseMoney(value);
        } catch (ParseException e) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        this.remarks = remarks;
    }

    /**
     * Returns true if a given string is a valid policy field.
     */
    public static boolean isValidVariable(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return "Liability{"
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

        if (!(o instanceof Liability)) {
            return false;
        }

        Liability liability = (Liability) o;
        return getName().equals(liability.getName())
                && getType().equals(liability.getType())
                && getValue().equals(liability.getValue())
                && getRemarks().equals(liability.getRemarks());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getType(), getValue(), getRemarks());
    }

    @Override
    public boolean isPossibleDuplicate(Attribute other) {
        if (other instanceof Liability) {
            return getName().equalsIgnoreCase(((Liability) other).getName());
        } else {
            return false;
        }
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
