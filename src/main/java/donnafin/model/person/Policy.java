package donnafin.model.person;

import static donnafin.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import donnafin.commons.core.types.Money;
import donnafin.logic.parser.ParserUtil;
import donnafin.logic.parser.exceptions.ParseException;

/**
 * Represents a Person's policy in DonnaFin.
 */
public class Policy implements Attribute {

    public static final String MESSAGE_CONSTRAINTS = "Insert policy constraint here";
    public static final String VALIDATION_REGEX = "[\\s\\S]*";
    public final String name;
    public final String insurer;
    public final Money totalValueInsured;
    public final Money yearlyPremiums;
    public final Money commission;

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
        this.name = name;
        this.insurer = insurer;
        try {
            this.totalValueInsured = ParserUtil.parseMoney(totalValueInsured);
            this.yearlyPremiums = ParserUtil.parseMoney(yearlyPremiums);
            this.commission = ParserUtil.parseMoney(commission);
        } catch (ParseException pe) {
            throw new IllegalArgumentException(Policy.MESSAGE_CONSTRAINTS);
        }
    }

    @Override
    public String toString() {
        return "Policy{"
                + "name='" + name + '\''
                + ", insurer='" + insurer + '\''
                + ", totalValueInsured='" + totalValueInsured + '\''
                + ", yearlyPremiums='" + yearlyPremiums + '\''
                + ", commission='" + commission + '\''
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
        return name.equals(policy.name)
                && insurer.equals(policy.insurer)
                && totalValueInsured.equals(policy.totalValueInsured)
                && yearlyPremiums.equals(policy.yearlyPremiums)
                && commission.equals(policy.commission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, insurer, totalValueInsured, yearlyPremiums, commission);
    }
}
