package donnafin.commons.core.types;

import java.util.Objects;

import donnafin.commons.exceptions.IllegalValueException;

public class Money {
    public static final String MONEY_ARITHMETIC_OVERFLOW =
            "Unable to calculate due to Integer limitations.";
    private static final String CURRENCY_SYMBOL = "$";
    private static final int CURRENCY_EXPONENT = 2;

    private final int value;

    /**
     * Create a Money object with value and currency as specified.
     *
     * @param valueInSmallestUnit integer value of the currency in its smallest unit (e.g. cent)
     */
    public Money(int valueInSmallestUnit) {
        this.value = valueInSmallestUnit;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        int absVal = Math.abs(value);
        int divisor = (int) Math.pow(10, CURRENCY_EXPONENT);

        String biggerUnitValue = "" + absVal / divisor;
        String sign = absVal == value ? " " : "-";

        int numDigitsSmallerValue = absVal % divisor != 0
                ? (int) Math.floor(Math.log10(absVal % divisor)) + 1
                : 1;
        String smallerUnitValue = "0".repeat(Math.max(0, CURRENCY_EXPONENT - numDigitsSmallerValue));
        smallerUnitValue += absVal % divisor;

        return String.format("%s%s %s.%s", sign, CURRENCY_SYMBOL, biggerUnitValue, smallerUnitValue);
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
