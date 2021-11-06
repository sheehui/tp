package donnafin.commons.core.types;

import java.util.Objects;

import donnafin.commons.exceptions.IllegalValueException;

public class Money {
    public static final String MONEY_ARITHMETIC_OVERFLOW =
            "Unable to calculate due to Integer limitations.";
    public static final String MESSAGE_CONSTRAINTS = "Monetary value fields should start with '$' followed by digits."
            + " 2 digits preceded by a '.' may be added at the end to indicate cents"
            + "\nExample: '$2.40', or '$2'";
    public static final String CURRENCY_SYMBOL = "$";
    private static final int CURRENCY_EXPONENT = 2;

    private final long value;

    /**
     * Create a Money object with value and currency as specified.
     *
     * @param valueInSmallestUnit integer value of the currency in its smallest unit (e.g. cent)
     */
    public Money(long valueInSmallestUnit) throws MoneyException {
        if (valueInSmallestUnit < 0) {
            throw new MoneyException("Negative monetary values are not supported");
        }
        this.value = valueInSmallestUnit;
    }

    public long getValue() {
        return value;
    }

    @Override
    public String toString() {
        long divisor = (long) Math.pow(10, CURRENCY_EXPONENT);

        String biggerUnitValue = "" + value / divisor;

        int numDigitsSmallerValue = value % divisor != 0
                ? (int) Math.floor(Math.log10(value % divisor)) + 1
                : 1;
        String smallerUnitValue = "0".repeat(Math.max(0, CURRENCY_EXPONENT - numDigitsSmallerValue));
        smallerUnitValue += value % divisor;

        return String.format("%s %s.%s", CURRENCY_SYMBOL, biggerUnitValue, smallerUnitValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Money)) {
            return false;
        }
        Money other = (Money) o;
        return value == other.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    /**
     * Perform addition on Money objects.
     *
     * @return result of adding two Money objects of the same currency.
     * @throws MoneyException if the Money objects involved are of different currencies or
     *                        results in an ArithmeticError (Integer overflow).
     */
    public static Money add(Money a, Money b) throws MoneyException {
        try {
            return new Money(Math.addExact(a.value, b.value));
        } catch (ArithmeticException e) {
            throw new MoneyException(Money.MONEY_ARITHMETIC_OVERFLOW);
        }
    }

    /**
     * Perform subtraction on Money objects.
     *
     * @return result of adding two Money objects of the same currency.
     * @throws MoneyException if the Money objects involved are of different currencies or
     *                        results in an ArithmeticError (Integer overflow).
     */
    public static Money subtract(Money a, Money b) throws MoneyException {
        try {
            if (b.getValue() > b.getValue()) {
                throw new ArithmeticException("Negative result after subtraction");
            }
            return new Money(Math.subtractExact(a.value, b.value));
        } catch (ArithmeticException e) {
            throw new MoneyException(Money.MONEY_ARITHMETIC_OVERFLOW);
        }
    }

    public static class MoneyException extends IllegalValueException {
        /**
         * @param message should contain relevant information on the failed constraint(s)
         */
        public MoneyException(String message) {
            super(message);
        }
    }
}
