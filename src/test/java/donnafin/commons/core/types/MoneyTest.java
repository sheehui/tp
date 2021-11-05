package donnafin.commons.core.types;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import donnafin.commons.core.types.Money.MoneyException;

public class MoneyTest {

    @Test
    public void getMoneyFromIntegerCheckValue() throws MoneyException {
        assertEquals(123, new Money(123).getValue());
    }

    @Test
    public void getMoneyFromNegativeValue_fails_throwsMoneyException() {
        assertThrows(MoneyException.class, () -> new Money(-123));
    }

    @Test
    public void checkMoneyOutput() throws MoneyException {
        assertEquals("$ 1.00", new Money(100).toString());
        assertEquals("$ 3.00", new Money(300).toString());
        assertEquals("$ 1.20", new Money(120).toString());
        assertEquals("$ 1.23", new Money(123).toString());
    }

    @Test
    public void testFullCentsString() throws MoneyException {
        assertEquals("$ 1.23", new Money(123).toString());
    }

    @Test
    public void testPaddedCentsString() throws MoneyException {
        assertEquals("$ 1.03", new Money(103).toString());
    }

    @Test
    public void testOnlyCentsString() throws MoneyException {
        assertEquals("$ 0.03", new Money(3).toString());
        assertEquals("$ 0.00", new Money(0).toString());
    }

    @Test
    public void mathTest() throws MoneyException {
        Money oneCent = new Money(1);
        Money oneDollar = new Money(100);
        assertEquals(101, Money.add(oneCent, oneDollar).getValue());
        assertEquals(99, Money.subtract(oneDollar, oneCent).getValue());
    }

    @Test
    public void safeMaths() throws MoneyException {
        Money maxValue = new Money(Long.MAX_VALUE);
        Money minValue = new Money(0);
        Money oneCent = new Money(1);
        assertThrows(MoneyException.class, () -> Money.add(maxValue, oneCent));
        assertThrows(MoneyException.class, () -> Money.subtract(minValue, oneCent));
        assertThrows(MoneyException.class, () -> Money.subtract(new Money(1), new Money(100)));
    }

    @Test
    public void equals() throws MoneyException {
        Money oneDollar = new Money(100);
        Money oneDollarAgain = new Money(100);

        assertEquals(oneDollar, oneDollar);
        assertEquals(oneDollar, oneDollarAgain);
        assertEquals(oneDollar.getValue(), oneDollarAgain.getValue());
        assertEquals(oneDollar.toString(), oneDollarAgain.toString());
        assertNotEquals(oneDollar, "$ 1.00");
    }
}
