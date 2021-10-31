package donnafin.model.person;

import static donnafin.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import donnafin.commons.core.types.Money;
import donnafin.commons.core.types.Money.MoneyException;

public class PolicyTest {

    private static final String VALID_NAME = "Golden Mile";
    private static final String VALID_INSURER = "AIA";
    private static final String VALID_TOTAL_VALUE_INSURED = "$2000";
    private static final String VALID_YEARLY_PREMIUMS = "$100";
    private static final String VALID_COMMISSION = "$200";
    private static final Policy VALID_POLICY = new Policy(VALID_NAME, VALID_INSURER, VALID_TOTAL_VALUE_INSURED,
            VALID_YEARLY_PREMIUMS, VALID_COMMISSION);


    @Test
    public void constructor_allNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Policy(null, null, null,
                null, null));
    }

    @Test
    public void constructor_parametersNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Policy(null, VALID_INSURER, VALID_TOTAL_VALUE_INSURED,
                VALID_YEARLY_PREMIUMS, VALID_COMMISSION));
        assertThrows(NullPointerException.class, () -> new Policy(VALID_NAME, null, VALID_TOTAL_VALUE_INSURED,
                VALID_YEARLY_PREMIUMS, VALID_COMMISSION));
        assertThrows(NullPointerException.class, () -> new Policy(VALID_NAME, VALID_INSURER, null,
                VALID_YEARLY_PREMIUMS, VALID_COMMISSION));
        assertThrows(NullPointerException.class, () -> new Policy(VALID_NAME, VALID_INSURER, VALID_TOTAL_VALUE_INSURED,
                null, VALID_COMMISSION));
        assertThrows(NullPointerException.class, () -> new Policy(VALID_NAME, VALID_INSURER, VALID_TOTAL_VALUE_INSURED,
                VALID_YEARLY_PREMIUMS, null));
    }

    @Test
    public void constructor_invalidCommission_throwsIllegalArgumentException() {
        String invalidCommission = "one million dollars";
        String emptyCommission = "";
        String missingDollar = "200";
        assertThrows(IllegalArgumentException.class, () -> new Policy(VALID_NAME, VALID_INSURER,
                VALID_TOTAL_VALUE_INSURED, VALID_YEARLY_PREMIUMS, invalidCommission));
        assertThrows(IllegalArgumentException.class, () -> new Policy(VALID_NAME, VALID_INSURER,
                VALID_TOTAL_VALUE_INSURED, VALID_YEARLY_PREMIUMS, emptyCommission));
        assertThrows(IllegalArgumentException.class, () -> new Policy(VALID_NAME, VALID_INSURER,
                VALID_TOTAL_VALUE_INSURED, VALID_YEARLY_PREMIUMS, missingDollar));
    }

    @Test
    public void constructor_invalidTotalValueInsured_throwsIllegalArgumentException() {
        String invalidTotalValueInsured = "one million dollars";
        String emptyTotalValueInsured = "";
        String missingDollar = "2000";
        assertThrows(IllegalArgumentException.class, () -> new Policy(VALID_NAME, VALID_INSURER,
                invalidTotalValueInsured, VALID_YEARLY_PREMIUMS, VALID_COMMISSION));
        assertThrows(IllegalArgumentException.class, () -> new Policy(VALID_NAME, VALID_INSURER,
                emptyTotalValueInsured, VALID_YEARLY_PREMIUMS, VALID_COMMISSION));
        assertThrows(IllegalArgumentException.class, () -> new Policy(VALID_NAME, "AIA", missingDollar,
                VALID_YEARLY_PREMIUMS, VALID_COMMISSION));
    }

    @Test
    public void constructor_invalidYearlyPremiums_throwsIllegalArgumentException() {
        String invalidYearlyPremiums = "one million dollars";
        String emptyYearlyPremiums = "";
        String missingDollar = "100";
        assertThrows(IllegalArgumentException.class, () -> new Policy(VALID_NAME, VALID_INSURER,
                VALID_TOTAL_VALUE_INSURED, invalidYearlyPremiums, VALID_COMMISSION));
        assertThrows(IllegalArgumentException.class, () -> new Policy(VALID_NAME, VALID_INSURER,
                VALID_TOTAL_VALUE_INSURED, emptyYearlyPremiums, VALID_COMMISSION));
        assertThrows(IllegalArgumentException.class, () -> new Policy(VALID_NAME, VALID_INSURER,
                VALID_TOTAL_VALUE_INSURED, missingDollar, VALID_COMMISSION));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "MediPro \n Lux";
        String emptyName = " ";
        assertThrows(IllegalArgumentException.class, () -> new Policy(invalidName, VALID_INSURER,
                VALID_TOTAL_VALUE_INSURED, VALID_YEARLY_PREMIUMS, VALID_COMMISSION));
        assertThrows(IllegalArgumentException.class, () -> new Policy(emptyName, VALID_INSURER,
                VALID_TOTAL_VALUE_INSURED, VALID_YEARLY_PREMIUMS, VALID_COMMISSION));
    }


    @Test
    public void constructor_invalidInsurer_throwsIllegalArgumentException() {
        String invalidInsurer = "AIA \n";
        String emptyInsurer = " ";
        assertThrows(IllegalArgumentException.class, () -> new Policy(VALID_NAME, invalidInsurer,
                VALID_TOTAL_VALUE_INSURED, VALID_YEARLY_PREMIUMS, VALID_COMMISSION));
        assertThrows(IllegalArgumentException.class, () -> new Policy(VALID_NAME, emptyInsurer,
                VALID_TOTAL_VALUE_INSURED, VALID_YEARLY_PREMIUMS, VALID_COMMISSION));
    }

    @Test
    public void variable_commissionToString_valid() throws MoneyException {
        Money commission = new Money(20000);
        assertEquals(commission.toString(), VALID_POLICY.getCommissionToString());
    }

    @Test
    public void variable_yearlyPremiumsToString_valid() throws MoneyException {
        Money yearlyPremiums = new Money(10000);
        assertEquals(yearlyPremiums.toString(), VALID_POLICY.getYearlyPremiumsToString());
    }

    @Test
    public void variable_totalValueInsuredToString_valid() throws MoneyException {
        Money totalValueInsured = new Money(200000);
        assertEquals(totalValueInsured.toString(), VALID_POLICY.getTotalValueInsuredToString());
    }

    @Test
    public void testEquals() {
        final Policy samePolicy = new Policy(
                VALID_NAME, VALID_INSURER, VALID_TOTAL_VALUE_INSURED,
                VALID_YEARLY_PREMIUMS, VALID_COMMISSION
        );
        final Policy otherPolicy = new Policy(
                VALID_NAME + "something", VALID_INSURER, VALID_TOTAL_VALUE_INSURED,
                VALID_YEARLY_PREMIUMS, VALID_COMMISSION
        );
        assertEquals(VALID_POLICY, VALID_POLICY);
        assertEquals(samePolicy, VALID_POLICY);
        assertNotEquals(samePolicy, "a string");
        assertNotEquals(otherPolicy, samePolicy);
        assertNotEquals(null, samePolicy);
    }

    @Test
    public void aggregateFunctionWorks() {
        // EP: 1 policy
        assertEquals(
                "Total Policy Commissions: $200.00",
                Policy.TABLE_CONFIG.aggregatorLabelCreator.applyOn(List.of(VALID_POLICY))
        );

        // EP: 0 policy
        assertEquals("", Policy.TABLE_CONFIG.aggregatorLabelCreator.applyOn(List.of()));

        // EP: Commissions exceeds Long.MAX_VALUE
        String maxMoneyVal = "$" + Long.MAX_VALUE / 100;
        assertEquals(
                "Total Policy Commissions: $276701161105643274.00",
                Policy.TABLE_CONFIG.aggregatorLabelCreator.applyOn(List.of(
                        new Policy("Test", "Test", maxMoneyVal, maxMoneyVal, maxMoneyVal),
                        new Policy("Test", "Test", maxMoneyVal, maxMoneyVal, maxMoneyVal),
                        new Policy("Test", "Test", maxMoneyVal, maxMoneyVal, maxMoneyVal)
                ))
        );
    }
}
