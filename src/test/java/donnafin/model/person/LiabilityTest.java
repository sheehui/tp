package donnafin.model.person;

import static donnafin.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import donnafin.commons.core.types.Money;
import donnafin.commons.core.types.Money.MoneyException;

public class LiabilityTest {
    private static final String VALID_NAME = "Mortgage debt";
    private static final String VALID_TYPE = "Debt";
    private static final String VALID_VALUE = "$2000";
    private static final String VALID_REMARKS = "23 year loan from DBS Bank.";
    private static final Liability VALID_LIABILITY = new Liability(VALID_NAME, VALID_TYPE, VALID_VALUE,
            VALID_REMARKS);


    @Test
    public void constructor_allNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Liability(null, null, null,
                null));
    }

    @Test
    public void constructor_parametersNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Liability(null, VALID_TYPE, VALID_VALUE,
                VALID_REMARKS));
        assertThrows(NullPointerException.class, () -> new Liability(VALID_NAME, null, VALID_VALUE,
                VALID_REMARKS));
        assertThrows(NullPointerException.class, () -> new Liability(VALID_NAME, VALID_TYPE, null,
                VALID_REMARKS));
        assertThrows(NullPointerException.class, () -> new Liability(VALID_NAME, VALID_TYPE, VALID_VALUE,
                null));
    }

    @Test
    public void constructor_invalidRemarks_throwsIllegalArgumentException() {
        String invalidRemarks = "one million dollars \n";
        String emptyRemarks = " ";
        assertThrows(IllegalArgumentException.class, () -> new Liability(VALID_NAME, VALID_TYPE, VALID_VALUE,
                invalidRemarks));
        assertThrows(IllegalArgumentException.class, () -> new Liability(VALID_NAME, VALID_TYPE, VALID_VALUE,
                emptyRemarks));
    }

    @Test
    public void constructor_invalidValue_throwsIllegalArgumentException() {
        String invalidValue = "one million dollars";
        String emptyValue = "";
        String missingDollar = "2000";
        assertThrows(IllegalArgumentException.class, () -> new Liability(VALID_NAME, VALID_TYPE, invalidValue,
                VALID_REMARKS));
        assertThrows(IllegalArgumentException.class, () -> new Liability(VALID_NAME, VALID_TYPE, emptyValue,
                VALID_REMARKS));
        assertThrows(IllegalArgumentException.class, () -> new Liability(VALID_NAME, VALID_TYPE, missingDollar,
                VALID_REMARKS));
    }

    @Test
    public void constructor_invalidType_throwsIllegalArgumentException() {
        String invalidType = "debt \n collateral";
        String emptyType = " ";
        assertThrows(IllegalArgumentException.class, () -> new Liability(VALID_NAME, invalidType, VALID_VALUE,
                VALID_REMARKS));
        assertThrows(IllegalArgumentException.class, () -> new Liability(VALID_NAME, emptyType, VALID_VALUE,
                VALID_REMARKS));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "john \n doe";
        String emptyName = " ";
        assertThrows(IllegalArgumentException.class, () -> new Liability(invalidName, VALID_TYPE, VALID_VALUE,
                VALID_REMARKS));
        assertThrows(IllegalArgumentException.class, () -> new Liability(emptyName, VALID_TYPE, VALID_VALUE,
                VALID_REMARKS));
    }

    @Test
    public void variable_commissionToString_valid() throws MoneyException {
        Money value = new Money(200000);
        assertEquals(value.toString(), VALID_LIABILITY.getValueToString());
    }

    @Test
    public void testEquals() {
        final Liability sameLiability = new Liability(VALID_NAME, VALID_TYPE, VALID_VALUE, VALID_REMARKS);
        final Liability otherLiability =
                new Liability(VALID_NAME + " something", VALID_TYPE, VALID_VALUE, VALID_REMARKS);
        assertEquals(VALID_LIABILITY, VALID_LIABILITY);
        assertEquals(sameLiability, VALID_LIABILITY);
        assertNotEquals(sameLiability, "a string");
        assertNotEquals(otherLiability, sameLiability);
        assertNotEquals(null, sameLiability);
    }

    @Test
    public void aggregateFunctionWorks() {
        // EP: 1 Liability
        assertEquals(
                "Total Liability Value: $2000.00",
                Liability.TABLE_CONFIG.aggregatorLabelCreator.applyOn(List.of(VALID_LIABILITY))
        );

        // EP: 0 Liability
        assertEquals("", Liability.TABLE_CONFIG.aggregatorLabelCreator.applyOn(List.of()));

        // EP: Value exceeds Long.MAX_VALUE
        String maxMoneyVal = "$" + Long.MAX_VALUE / 100;
        assertEquals(
                "Total Liability Value: $276701161105643274.00",
                Liability.TABLE_CONFIG.aggregatorLabelCreator.applyOn(List.of(
                        new Liability("Test", "Test", maxMoneyVal, "test"),
                        new Liability("Test", "Test", maxMoneyVal, "test"),
                        new Liability("Test", "Test", maxMoneyVal, "test")
                ))
        );
    }
}
