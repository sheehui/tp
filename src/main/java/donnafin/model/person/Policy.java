package donnafin.model.person;

import java.util.List;
import java.util.Objects;

import static donnafin.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Person's policy in DonnaFin.
 */
public class Policy implements Attribute {

    public static final String MESSAGE_CONSTRAINTS = "Insert policy constraint here";
    public static final String VALIDATION_REGEX = "[\\s\\S]*";
    public final String name;
    public final String insurer;
    public final String totalValueInsured;
    public final String yearlyPremiums;
    public final String commission;

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
        this.totalValueInsured = totalValueInsured;
        this.yearlyPremiums = yearlyPremiums;
        this.commission = commission;
    }

    /**
     * Returns true if a given string is a valid policy name
     */
    public static boolean isValidPolicy(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return "Policy{" +
                "name='" + name + '\'' +
                ", insurer='" + insurer + '\'' +
                ", totalValueInsured='" + totalValueInsured + '\'' +
                ", yearlyPremiums='" + yearlyPremiums + '\'' +
                ", commission='" + commission + '\'' +
                '}';
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
