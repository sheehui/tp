package donnafin.model.person;

import org.junit.jupiter.api.Test;

import static donnafin.testutil.Assert.assertThrows;

public class PolicyTest {

    @Test
    public void constructor_allNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Policy(null, null, null,
                null, null));
    }

    @Test
    public void constructor_parametersNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Policy(null, "AIA", "$2000",
                "$100", "$200"));
        assertThrows(NullPointerException.class, () -> new Policy("John", null, "$2000",
                "$100", "$200"));
        assertThrows(NullPointerException.class, () -> new Policy("Joe", "AIA", null,
                "$100", "$200"));
        assertThrows(NullPointerException.class, () -> new Policy("Jane", "AIA", "$2000",
                null, "$200"));
        assertThrows(NullPointerException.class, () -> new Policy("Hank", "AIA", "$2000",
                "$100", null));
    }

    @Test
    public void constructor_invalidCommission_throwsIllegalArgumentException() {
        String invalidCommission = "one million dollars";
        String emptyCommission = "";
        String missingDollar = "200";
        assertThrows(IllegalArgumentException.class, () -> new Policy("Hank", "AIA", "$2000",
                "$100", invalidCommission));
        assertThrows(IllegalArgumentException.class, () -> new Policy("Hank", "AIA", "$2000",
                "$100", emptyCommission));
        assertThrows(IllegalArgumentException.class, () -> new Policy("Hank", "AIA", "$2000",
                "$100", missingDollar));
    }

    @Test
    public void constructor_invalidTotalValueInsured_throwsIllegalArgumentException() {
        String invalidTotalValueInsured = "one million dollars";
        String emptyTotalValueInsured = "";
        String missingDollar = "2000";
        assertThrows(IllegalArgumentException.class, () -> new Policy("Hank", "AIA", invalidTotalValueInsured,
                "100", "200"));
        assertThrows(IllegalArgumentException.class, () -> new Policy("Hank", "AIA", emptyTotalValueInsured,
                "100", "200"));
        assertThrows(IllegalArgumentException.class, () -> new Policy("Hank", "AIA", missingDollar,
                "100", "200"));
    }

    @Test
    public void constructor_invalidYearlyPremiums_throwsIllegalArgumentException() {
        String invalidYearlyPremiums = "one million dollars";
        String emptyYearlyPremiums = "";
        String missingDollar = "100";
        assertThrows(IllegalArgumentException.class, () -> new Policy("Hank", "AIA", "2000",
                invalidYearlyPremiums, "200"));
        assertThrows(IllegalArgumentException.class, () -> new Policy("Hank", "AIA", "2000",
                emptyYearlyPremiums, "200"));
        assertThrows(IllegalArgumentException.class, () -> new Policy("Hank", "AIA", "2000",
                missingDollar, "200"));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "2";
        assertThrows(IllegalArgumentException.class, () -> new Policy(invalidName, "AIA", "2000",
                "100", "200"));
    }
}
