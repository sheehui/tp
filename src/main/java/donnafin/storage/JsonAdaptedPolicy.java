package donnafin.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import donnafin.commons.exceptions.IllegalValueException;
import donnafin.model.person.Policy;

/**
 * Jackson-friendly version of {@link Policy}.
 */
class JsonAdaptedPolicy {

    private final String policyName;
    private final String policyInsurer;
    public final String policyTotalValueInsured;
    public final String policyYearlyPremiums;
    public final String policyCommission;


    /**
     * Constructs a {@code JsonAdaptedPolicy} with the given {@code policyName}.
     */
    @JsonCreator
    public JsonAdaptedPolicy(@JsonProperty("name") String policyName, @JsonProperty("insurer") String policyInsurer,
                             @JsonProperty("totalValueInsured")String policyTotalValueInsured,
                             @JsonProperty("yearlyPremiums") String policyYearlyPremiums,
                             @JsonProperty("commission") String policyCommission) {
        this.policyName = policyName;
        this.policyCommission = policyCommission;
        this.policyInsurer = policyInsurer;
        this.policyYearlyPremiums = policyYearlyPremiums;
        this.policyTotalValueInsured = policyTotalValueInsured;
    }

    /**
     * Converts a given {@code Policy} into this class for Jackson use.
     */
    public JsonAdaptedPolicy(Policy source) {
        policyName = source.name;
        policyInsurer = source.insurer;
        policyYearlyPremiums = source.yearlyPremiums;
        policyTotalValueInsured = source.totalValueInsured;
        policyCommission = source.commission;
    }

    @JsonValue
    public String getPolicyName() {
        return policyName;
    }

    @JsonValue
    public String getPolicyCommission() {
        return policyCommission;
    }

    @JsonValue
    public String getPolicyInsurer() {
        return policyInsurer;
    }

    @JsonValue
    public String getPolicyTotalValueInsured() {
        return policyTotalValueInsured;
    }

    @JsonValue
    public String getPolicyYearlyPremiums() {
        return policyYearlyPremiums;
    }

    /**
     * Converts this Jackson-friendly adapted policy object into the model's {@code Policy} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted policy.
     */
    public Policy toModelType() throws IllegalValueException {
        if (!Policy.isValidPolicy(policyName)) {
            throw new IllegalValueException(Policy.MESSAGE_CONSTRAINTS);
        }
        return new Policy(policyName, policyInsurer, policyTotalValueInsured, policyYearlyPremiums, policyCommission);
    }
}
