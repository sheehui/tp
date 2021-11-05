package donnafin.logic.parser;

import static donnafin.logic.parser.CliSyntax.PREFIX_COMMISSION;
import static donnafin.logic.parser.CliSyntax.PREFIX_INSURED_VALUE;
import static donnafin.logic.parser.CliSyntax.PREFIX_INSURER;
import static donnafin.logic.parser.CliSyntax.PREFIX_NAME;
import static donnafin.logic.parser.CliSyntax.PREFIX_REMARKS;
import static donnafin.logic.parser.CliSyntax.PREFIX_TYPE;
import static donnafin.logic.parser.CliSyntax.PREFIX_VALUE;
import static donnafin.logic.parser.CliSyntax.PREFIX_YEARLY_PREMIUM;

import java.util.NoSuchElementException;
import java.util.Objects;

import donnafin.commons.core.types.Money;
import donnafin.logic.PersonAdapter;
import donnafin.logic.commands.AppendCommand;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.model.person.Asset;
import donnafin.model.person.Liability;
import donnafin.model.person.Policy;
import donnafin.ui.Ui;

public class AppendCommandParser implements Parser<AppendCommand> {

    private final Ui.ViewFinderState currentTab;
    private final Prefix[] prefixes;
    private final PersonAdapter personAdapter;

    /**
     * Constructor for parser for append command.
     */
    public AppendCommandParser(Ui.ViewFinderState currentTab, PersonAdapter personAdapter) throws ParseException {
        this.currentTab = currentTab;
        this.personAdapter = personAdapter;
        switch (currentTab) {
        case POLICIES:
            prefixes = new Prefix[]{
                PREFIX_NAME,
                PREFIX_INSURER,
                PREFIX_INSURED_VALUE,
                PREFIX_YEARLY_PREMIUM,
                PREFIX_COMMISSION
            };
            break;
        case ASSETS:
            // fallthrough
        case LIABILITIES:
            prefixes = new Prefix[]{ PREFIX_NAME, PREFIX_TYPE, PREFIX_VALUE, PREFIX_REMARKS };
            break;
        default:
            throw new ParseException("Invalid tab for append.");
        }
    }

    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @param args user input representing policy, liability or asset.
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public AppendCommand parse(String args) throws ParseException {
        Objects.requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, prefixes);

        switch (currentTab) {
        case POLICIES:
            return parsePolicy(argMultimap);
        case ASSETS:
            return parseAsset(argMultimap);
        case LIABILITIES:
            return parseLiability(argMultimap);
        default:
            throw new ParseException("Invalid tab for append.");
        }
    }

    private AppendCommand parseAsset(ArgumentMultimap argMultimap) throws ParseException {
        String name;
        String type;
        Money value;
        String remarks;

        try {
            name = argMultimap.getValue(PREFIX_NAME).orElseThrow();
            type = argMultimap.getValue(PREFIX_TYPE).orElseThrow();
            value = ParserUtil.parseMoney(argMultimap.getValue(PREFIX_VALUE).orElseThrow());
            remarks = argMultimap.getValue(PREFIX_REMARKS).orElse("");
        } catch (NoSuchElementException e) {
            throw new ParseException(Asset.MESSAGE_CONSTRAINTS);
        }

        Asset newAsset = null;
        try {
            newAsset = new Asset(name, type, value.toString(), remarks);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
        return new AppendCommand(personAdapter, newAsset);
    }

    private AppendCommand parseLiability(ArgumentMultimap argMultimap) throws ParseException {
        String name;
        String type;
        Money value;
        String remarks;

        try {
            name = argMultimap.getValue(PREFIX_NAME).orElseThrow();
            type = argMultimap.getValue(PREFIX_TYPE).orElseThrow();
            value = ParserUtil.parseMoney(argMultimap.getValue(PREFIX_VALUE).orElseThrow());
            remarks = argMultimap.getValue(PREFIX_REMARKS).orElse("");
        } catch (NoSuchElementException e) {
            throw new ParseException(Liability.MESSAGE_CONSTRAINTS);
        }

        Liability newLiability = null;
        try {
            newLiability = new Liability(name, type, value.toString(), remarks);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
        return new AppendCommand(personAdapter, newLiability);
    }

    private AppendCommand parsePolicy(ArgumentMultimap argMultimap) throws ParseException {
        String name;
        String insurer;
        Money insuredValue;
        Money premium;
        Money commission;
        try {
            name = argMultimap.getValue(PREFIX_NAME).orElseThrow();
            insurer = argMultimap.getValue(PREFIX_INSURER).orElseThrow();
            insuredValue = ParserUtil.parseMoney(argMultimap.getValue(PREFIX_INSURED_VALUE).orElseThrow());
            premium = ParserUtil.parseMoney(argMultimap.getValue(PREFIX_YEARLY_PREMIUM).orElseThrow());
            commission = ParserUtil.parseMoney(argMultimap.getValue(PREFIX_COMMISSION).orElseThrow());
        } catch (NoSuchElementException e) {
            throw new ParseException(Policy.MESSAGE_CONSTRAINTS);
        }
        Policy newPolicy = new Policy(
                name, insurer, insuredValue.toString(), premium.toString(), commission.toString());
        return new AppendCommand(personAdapter, newPolicy);
    }
}
