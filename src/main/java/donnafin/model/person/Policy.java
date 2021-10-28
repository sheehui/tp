package donnafin.model.person;

import static donnafin.commons.util.AppUtil.checkArgument;
import static donnafin.commons.util.CollectionUtil.requireAllNonNull;

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

    public static final String MESSAGE_CONSTRAINTS = "Policy name and insurer should not contain new lines or "
            + "start with empty spaces.";
    public static final String VALIDATION_REGEX = "[^\\s].*";
    public static final AttributeTable.TableConfig<Policy> TABLE_CONFIG = new AttributeTable.TableConfig<>(
        "Policies",
        List.of(
                    new AttributeTable.ColumnConfig("Policy Name", "name", 300),
                    new AttributeTable.ColumnConfig("Insurer", "insurer", 100),
                    new AttributeTable.ColumnConfig("Insured Value", "totalValueInsuredToString", 100),
                    new AttributeTable.ColumnConfig("Yearly Premium", "yearlyPremiumsToString", 100),
                    new AttributeTable.ColumnConfig("Commission", "commissionToString", 100)
            ),
        policyCol -> {
            Money acc = new Money(0);
            try {
                for (Policy policy : policyCol) {
                    Money commission = policy.getCommission();
                    acc = Money.add(acc, commission);
                }
            } catch (Money.MoneyException e) {
                return "-";
            }
            return "Total Policy Commissions: " + acc;
        }
    );
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
            throw new IllegalArgumentException(Money.MESSAGE_CONSTRAINTS);
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
