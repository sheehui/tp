package donnafin.commons.core.types;

import java.util.Currency;
import java.util.Objects;

import donnafin.commons.exceptions.IllegalValueException;

public class Money {
    public static final String MONEY_MISMATCH_CURRENCY =
            "Arithmetic on different currencies is not supported.";
    public static final String MONEY_ARITHMETIC_OVERFLOW =
            "Unable to calculate due to Integer limitations.";
    private static final Currency DEFAULT_CURRENCY = Currency.getInstance("SGD");

    private final int value;
    private final Currency currency;

    /**
     * Create a Money object with value and currency as specified.
     *
     * @param valueInSmallestUnit integer value of the currency in its smallest unit (e.g. cent)
     */
    public Money(int valueInSmallestUnit) {
        this(valueInSmallestUnit, Money.DEFAULT_CURRENCY);
    }

    /**
     * Create a Money object with value and currency as specified.
     *
     * @param valueInSmallestUnit integer value of the currency in its smallest unit (e.g. cent)
     * @param currency the currency being used (SGD if omitted).
     */
    public Money(int valueInSmallestUnit, Currency currency) {
        Objects.requireNonNull(currency);
        this.value = valueInSmallestUnit;
        this.currency = currency;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        int fractionDigits = this.currency.getDefaultFractionDigits();
        int absVal = Math.abs(value);
        int divisor = (int) Math.pow(10, fractionDigits);

        String biggerUnitValue = "" + absVal / divisor;
        String sign = absVal == value ? " " : "-";

        int numDigitsSmallerValue = (int) Math.floor(Math.log10(absVal % divisor)) + 1;
        String smallerUnitValue = "0".repeat(Math.max(0, fractionDigits - numDigitsSmallerValue));
        smallerUnitValue += absVal % divisor;

        return String.format("%s%s %s.%s", sign, currency.getSymbol(), biggerUnitValue, smallerUnitValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Money)) {
            return false;
        }
        Money money = (Money) o;
        return value == money.value && currency.equals(money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, currency);
    }

    /**
     * Perform addition on Money objects.
     *
     * @return result of adding two Money objects of the same currency.
     * @throws MoneyException if the Money objects involved are of different currencies or
     *                        results in an ArithmeticError (Integer overflow).
     */
    public static Money add(Money a, Money b) throws MoneyException {
        if (!(a.currency.equals(b.currency))) {
            throw new MoneyException(Money.MONEY_MISMATCH_CURRENCY);
        }
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
        if (!(a.currency.equals(b.currency))) {
            throw new MoneyException(Money.MONEY_MISMATCH_CURRENCY);
        }
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
