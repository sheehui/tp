package donnafin.logic.parser;

import static java.util.Objects.requireNonNull;

import java.math.BigInteger;
import java.util.Objects;

import donnafin.commons.core.types.Index;
import donnafin.commons.core.types.Money;
import donnafin.commons.util.StringUtil;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.model.person.Address;
import donnafin.model.person.Email;
import donnafin.model.person.Name;
import donnafin.model.person.Phone;
import donnafin.ui.Ui;
import donnafin.ui.Ui.ViewFinderState;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    private static final String ATTRIBUTE_DELIMITER = ";;;";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Checks {@code indexString} if it is larger than Max Integer Value.
     *
     * @throws NumberFormatException if specified index string is larger than max Integer.
     * @throws ParseException if specified index string is non-integer.
     */
    public static void checkIntegerMax(String indexString) throws NumberFormatException, ParseException {
        String trimmedIndex = indexString.trim();
        BigInteger maxInt = BigInteger.valueOf(Integer.MAX_VALUE);
        BigInteger value;

        try {
            value = new BigInteger(trimmedIndex);
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }

        if (value.compareTo(maxInt) > 0) {
            throw new NumberFormatException();
        }
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String money} into a {@code Money}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code String money} is invalid.
     */
    public static Money parseMoney(String money) throws ParseException {
        requireNonNull(money);
        String trimmedInput = money.trim();

        // Handling Default currency $XYZ or $XYZ.AB
        final String positiveInts = "([1-9]\\d*)"; // rejects starting non-zero ints with a zero (e.g. '02');
        final String regexOptionalCents = "(\\.\\d{2})?";
        final String regexDollarFormat = "^\\$\\s*(" + positiveInts + "|(0))" + regexOptionalCents + "$";

        if (!trimmedInput.matches(regexDollarFormat)) {
            throw new ParseException(
                    String.format(
                            "Input string '%s' does not match monetary value format.\n%s",
                            trimmedInput,
                            Money.MESSAGE_CONSTRAINTS)
            );
        }

        String decimalString = trimmedInput.replace(Money.CURRENCY_SYMBOL, "").replace(" ", "");
        if (!decimalString.contains(".")) {
            decimalString += ".00";
        }
        try {
            long value = Long.parseLong(decimalString.replace(".", ""));
            return new Money(value);
        } catch (Money.MoneyException e) {
            throw new ParseException(e.getMessage());
        } catch (NumberFormatException e) {
            throw new ParseException(
                String.format("'%s' exceeds maximum monetary value (~$92 quadrillion).", trimmedInput));
        }
    }

    /**
     * Parse a string that is expected to describe a tab in {@code ClientView} into
     * the {@code Ui.ViewFinderState} enum.
     */
    public static ViewFinderState parseTab(String tabName) throws ParseException {
        Objects.requireNonNull(tabName);
        tabName = tabName.trim().toUpperCase();
        switch (tabName) {
        case "C":
            // fallthrough
        case "CONTACT":
            // fallthrough
        case "CONTACTS":
            return Ui.ViewFinderState.CONTACT;
        case "P":
            // fallthrough
        case "POLICY":
            // fallthrough
        case "POLICIES":
            return ViewFinderState.POLICIES;
        case "A":
            // fallthrough
        case "ASSET":
            // fallthrough
        case "ASSETS":
            return ViewFinderState.ASSETS;
        case "L":
            // fallthrough
        case "LIABILITY":
            // fallthrough
        case "LIABILITIES":
            return ViewFinderState.LIABILITIES;
        case "N":
            // fallthrough
        case "NOTE":
            // fallthrough
        case "NOTES":
            return Ui.ViewFinderState.NOTES;
        default:
            throw new ParseException(String.format("Input command is invalid: '%s' does not match any tab", tabName));
        }
    }
}
