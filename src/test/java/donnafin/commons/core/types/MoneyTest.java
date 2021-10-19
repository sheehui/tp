package donnafin.commons.core.types;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class MoneyTest {

    @Test
    public void getMoneyFromIntegerCheckValue() {
        assertEquals(123, new Money(123).getValue());
        assertEquals(-123, new Money(-123).getValue());
    }

    @Test
    public void checkMoneyOutput() {
        assertEquals(" $ 1.00", new Money(100).toString());
        assertEquals(" $ 3.00", new Money(300).toString());
        assertEquals("-$ 1.00", new Money(-100).toString());
        assertEquals(" $ 1.20", new Money(120).toString());
        assertEquals(" $ 1.23", new Money(123).toString());
    }

    @Test
    public void testFullCentsString() {
        assertEquals(" $ 1.23", new Money(123).toString());
        assertEquals("-$ 1.23", new Money(-123).toString());
    }

    @Test
    public void testPaddedCentsString() {
        assertEquals(" $ 1.03", new Money(103).toString());
        assertEquals("-$ 1.03", new Money(-103).toString());
    }

    @Test
    public void testOnlyCentsString() {
        assertEquals(" $ 0.03", new Money(3).toString());
        assertEquals("-$ 0.03", new Money(-3).toString());
    }

    @Test
    public void mathTest() throws Money.MoneyException {
        Money oneCent = new Money(1);
        Money oneDollar = new Money(100);
        assertEquals(101, Money.add(oneCent, oneDollar).getValue());
        assertEquals(99, Money.subtract(oneDollar, oneCent).getValue());
        assertEquals(-99, Money.subtract(oneCent, oneDollar).getValue());
    }

    @Test
    public void safeMaths() {
        Money maxValue = new Money(Integer.MAX_VALUE);
        Money minValue = new Money(Integer.MIN_VALUE);
        Money oneCent = new Money(1);
        assertThrows(Money.MoneyException.class, () -> Money.add(maxValue, oneCent));
        assertThrows(Money.MoneyException.class, () -> Money.subtract(minValue, oneCent));
    }

    @Test
    public void equals() {
        Money oneDollar = new Money(100);
        Money oneDollarAgain = new Money(100);
        Money negativeOneDollar = new Money(-100);
        Money negativeOneDollarAgain = new Money(-100);

        assertEquals(oneDollar, oneDollar);
        assertEquals(oneDollar, oneDollarAgain);
        assertEquals(oneDollar.getValue(), oneDollarAgain.getValue());
        assertEquals(oneDollar.toString(), oneDollarAgain.toString());

        assertNotEquals(negativeOneDollar, oneDollar);

        assertEquals(negativeOneDollar, negativeOneDollar);
        assertEquals(negativeOneDollar, negativeOneDollarAgain);
        assertEquals(negativeOneDollar.getValue(), negativeOneDollarAgain.getValue());
        assertEquals(negativeOneDollar.toString(), negativeOneDollarAgain.toString());
    }
}
