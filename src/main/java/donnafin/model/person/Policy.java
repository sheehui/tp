//@@author sheehui
package donnafin.model.person;

import static donnafin.commons.util.AppUtil.checkArgument;
import static donnafin.commons.util.CollectionUtil.requireAllNonNull;
import static donnafin.logic.parser.CliSyntax.PREFIX_COMMISSION;
import static donnafin.logic.parser.CliSyntax.PREFIX_INSURED_VALUE;
import static donnafin.logic.parser.CliSyntax.PREFIX_INSURER;
import static donnafin.logic.parser.CliSyntax.PREFIX_NAME;
import static donnafin.logic.parser.CliSyntax.PREFIX_YEARLY_PREMIUM;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

import donnafin.commons.core.types.Money;
import donnafin.logic.parser.ParserUtil;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.ui.AttributeTable;

/**
 * Represents a Person's policy in DonnaFin.
 */
public class Policy implements Attribute {

    public static final String MESSAGE_CONSTRAINTS = "Policies must be specified with "
            + "\na name (non-empty string) "
            + "a insurer name (non-empty string) "
            + "\na value insured (positive monetary value) "
            + "a yearly premium (positive monetary value) "
            + "a commission (positive monetary value) "
            + "\nE.g. "
            + PREFIX_NAME + "Platinum Years "
            + PREFIX_INSURER + "FinAssurance Corp. "
            + PREFIX_INSURED_VALUE + "$100000 "
            + PREFIX_YEARLY_PREMIUM + "$100 "
            + PREFIX_COMMISSION + "$10 ";
    public static final String VALIDATION_REGEX = "[^\\s].*";
    //@@author bharathcs
    public static final AttributeTable.TableConfig<Policy> TABLE_CONFIG = new AttributeTable.TableConfig<>(
        "Policies",
        List.of(
                    new AttributeTable.ColumnConfig("Policy Name", "name", 200, 500),
                    new AttributeTable.ColumnConfig("Insurer", "insurer", 100, 250),
                    new AttributeTable.ColumnConfig("Insured Value", "totalValueInsuredToString", 200, 300),
                    new AttributeTable.ColumnConfig("Premium (yearly)", "yearlyPremiumsToString", 200, 300),
                    new AttributeTable.ColumnConfig("Commission", "commissionToString", 100, 250)
            ),
        policyCol -> policyCol.stream()
            .map(Policy::getCommission)
            .map(Money::getValue)
            .map(BigInteger::valueOf)
            .reduce(BigInteger::add)
            .map(i -> {
                BigInteger unit = BigInteger.valueOf(100);
                String cents = i.mod(unit).add(unit).toString().substring(1);
                String dollars = i.divide(unit).toString();
                return String.format("Total Policy Commissions: $%s.%s", dollars, cents);
            }).orElse("")
    );
    //@@author sheehui

    private final String name;
    private final String insurer;
    private final Money totalValueInsured;
    private final Money yearlyPremiums;
    private final Money commission;

    /**
     * Constructs a {@code Policy}.
     *
     * @param name A valid policy name.
     * @param insurer Name of insurer.
     * @param totalValueInsured Numerical value insured in Policy.
     * @param yearlyPremiums premiums offered by Policy.
     * @param commission Value of commission in this Policy.
     */
    public Policy(String name, String insurer, String totalValueInsured, String yearlyPremiums, String commission) {
        requireAllNonNull(name, insurer, totalValueInsured, yearlyPremiums, commission);
        checkArgument(isValidVariable(name), MESSAGE_CONSTRAINTS);
        checkArgument(isValidVariable(insurer), MESSAGE_CONSTRAINTS);
        this.name = name;
        this.insurer = insurer;
        try {
            this.totalValueInsured = ParserUtil.parseMoney(totalValueInsured);
            this.yearlyPremiums = ParserUtil.parseMoney(yearlyPremiums);
            this.commission = ParserUtil.parseMoney(commission);
        } catch (ParseException pe) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Returns true if a given string is a valid policy variable.
     */
    public static boolean isValidVariable(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return "Policy{"
                + "name='" + getName() + '\''
                + ", insurer='" + getInsurer() + '\''
                + ", totalValueInsured='" + getTotalValueInsured() + '\''
                + ", yearlyPremiums='" + getYearlyPremiums() + '\''
                + ", commission='" + getCommission() + '\''
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Policy)) {
            return false;
        }

        Policy policy = (Policy) o;
        return getName().equals(policy.getName())
                && getInsurer().equals(policy.getInsurer())
                && getTotalValueInsured().equals(policy.getTotalValueInsured())
                && getYearlyPremiums().equals(policy.getYearlyPremiums())
                && getCommission().equals(policy.getCommission());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getInsurer(), getTotalValueInsured(), getYearlyPremiums(), getCommission());
    }

    @Override
    public boolean isPossibleDuplicate(Attribute other) {
        if (other instanceof Policy) {
            return getName().equalsIgnoreCase(((Policy) other).getName());
        } else {
            return false;
        }
    }

    public String getName() {
        return name;
    }

    public String getInsurer() {
        return insurer;
    }

    public Money getTotalValueInsured() {
        return totalValueInsured;
    }

    public Money getYearlyPremiums() {
        return yearlyPremiums;
    }

    public Money getCommission() {
        return commission;
    }

    public String getCommissionToString() {
        return commission.toString();
    }

    public String getTotalValueInsuredToString() {
        return totalValueInsured.toString();
    }

    public String getYearlyPremiumsToString() {
        return yearlyPremiums.toString();
    }
}
