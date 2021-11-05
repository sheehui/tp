package donnafin.model.person;

import static donnafin.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import donnafin.commons.core.types.Money;
import donnafin.commons.core.types.Money.MoneyException;

public class AssetTest {

    private static final String VALID_NAME = "Good Class Bungalow";
    private static final String VALID_TYPE = "Property";
    private static final String VALID_VALUE = "$2000";
    private static final String VALID_REMARKS = "Paid in full. No debt.";
    private static final Asset VALID_ASSET = new Asset(VALID_NAME, VALID_TYPE, VALID_VALUE,
            VALID_REMARKS);

    @Test
    public void constructor_allNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Asset(null, null, null,
                null));
    }

    @Test
    public void constructor_parametersNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Asset(null, VALID_TYPE, VALID_VALUE,
                VALID_REMARKS));
        assertThrows(NullPointerException.class, () -> new Asset(VALID_NAME, null, VALID_VALUE,
                VALID_REMARKS));
        assertThrows(NullPointerException.class, () -> new Asset(VALID_NAME, VALID_TYPE, null,
                VALID_REMARKS));
        assertThrows(NullPointerException.class, () -> new Asset(VALID_NAME, VALID_TYPE, VALID_VALUE,
                null));
    }

    @Test
    public void constructor_invalidRemarks_throwsIllegalArgumentException() {
        String invalidRemarks = "one million dollars \n";
        String emptyRemarks = " ";
        assertThrows(IllegalArgumentException.class, () -> new Asset(VALID_NAME, VALID_TYPE, VALID_VALUE,
                invalidRemarks));
        assertThrows(IllegalArgumentException.class, () -> new Asset(VALID_NAME, VALID_TYPE, VALID_VALUE,
                emptyRemarks));
    }

    @Test
    public void constructor_invalidValue_throwsIllegalArgumentException() {
        String invalidValue = "one million dollars";
        String emptyValue = "";
        String missingDollar = "2000";
        assertThrows(IllegalArgumentException.class, () -> new Asset(VALID_NAME, VALID_TYPE, invalidValue,
                VALID_REMARKS));
        assertThrows(IllegalArgumentException.class, () -> new Asset(VALID_NAME, VALID_TYPE, emptyValue,
                VALID_REMARKS));
        assertThrows(IllegalArgumentException.class, () -> new Asset(VALID_NAME, VALID_TYPE, missingDollar,
                VALID_REMARKS));
    }

    @Test
    public void constructor_invalidType_throwsIllegalArgumentException() {
        String invalidType = "debt \n collateral";
        String emptyType = " ";
        assertThrows(IllegalArgumentException.class, () -> new Asset(VALID_NAME, invalidType, VALID_VALUE,
                VALID_REMARKS));
        assertThrows(IllegalArgumentException.class, () -> new Asset(VALID_NAME, emptyType, VALID_VALUE,
                VALID_REMARKS));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "john \n doe";
        String emptyName = " ";
        assertThrows(IllegalArgumentException.class, () -> new Asset(invalidName, VALID_TYPE, VALID_VALUE,
                VALID_REMARKS));
        assertThrows(IllegalArgumentException.class, () -> new Asset(emptyName, VALID_TYPE, VALID_VALUE,
                VALID_REMARKS));
    }

    @Test
    public void variable_commissionToString_valid() throws MoneyException {
        Money value = new Money(200000);
        assertEquals(value.toString(), VALID_ASSET.getValueToString());
    }

    @Test
    public void testEquals() {
        final Asset sameAsset = new Asset(VALID_NAME, VALID_TYPE, VALID_VALUE, VALID_REMARKS);
        final Asset otherAsset = new Asset(VALID_NAME + " something", VALID_TYPE, VALID_VALUE, VALID_REMARKS);
        assertEquals(VALID_ASSET, VALID_ASSET);
        assertEquals(sameAsset, VALID_ASSET);
        assertNotEquals(sameAsset, "a string");
        assertNotEquals(otherAsset, sameAsset);
        assertNotEquals(null, sameAsset);
    }

    @Test
    public void aggregateFunctionWorks() {
        // EP: 1 Asset
        assertEquals(
                "Total Asset Value: $2000.00",
                Asset.TABLE_CONFIG.aggregatorLabelCreator.applyOn(List.of(VALID_ASSET))
        );

        // EP: 0 Asset
        assertEquals("", Asset.TABLE_CONFIG.aggregatorLabelCreator.applyOn(List.of()));

        // EP: Value exceeds Long.MAX_VALUE
        String maxMoneyVal = "$" + Long.MAX_VALUE / 100;
        assertEquals(
                "Total Asset Value: $276701161105643274.00",
                Asset.TABLE_CONFIG.aggregatorLabelCreator.applyOn(List.of(
                        new Asset("Test", "Test", maxMoneyVal, "test"),
                        new Asset("Test", "Test", maxMoneyVal, "test"),
                        new Asset("Test", "Test", maxMoneyVal, "test")
                ))
        );
    }
}
