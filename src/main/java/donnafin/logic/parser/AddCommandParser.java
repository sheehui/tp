package donnafin.logic.parser;

import static donnafin.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static donnafin.logic.parser.CliSyntax.PREFIX_ASSETS;
import static donnafin.logic.parser.CliSyntax.PREFIX_COMMISSION;
import static donnafin.logic.parser.CliSyntax.PREFIX_EMAIL;
import static donnafin.logic.parser.CliSyntax.PREFIX_LIABILITIES;
import static donnafin.logic.parser.CliSyntax.PREFIX_NAME;
import static donnafin.logic.parser.CliSyntax.PREFIX_PHONE;
import static donnafin.logic.parser.CliSyntax.PREFIX_POLICY;
import static donnafin.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import donnafin.commons.core.Messages;
import donnafin.logic.commands.AddCommand;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.model.person.Address;
import donnafin.model.person.Assets;
import donnafin.model.person.Commission;
import donnafin.model.person.Email;
import donnafin.model.person.Liabilities;
import donnafin.model.person.Name;
import donnafin.model.person.Person;
import donnafin.model.person.Phone;
import donnafin.model.person.Policy;
import donnafin.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG,
                PREFIX_POLICY, PREFIX_LIABILITIES, PREFIX_COMMISSION, PREFIX_ASSETS);
        // Should check that all are present accept for the non required fields, policy and assets
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Set<Policy> policySet = ParserUtil.parsePolicies(argMultimap.getAllValues(PREFIX_POLICY));
        Liabilities liabilities = ParserUtil.parseLiabilities(argMultimap.getValue(PREFIX_LIABILITIES).get());
        Commission commission = ParserUtil.parseCommission(argMultimap.getValue(PREFIX_COMMISSION).get());
        Set<Assets> assetsSet = ParserUtil.parseAssets(argMultimap.getAllValues(PREFIX_ASSETS));

        Person person = new Person(name, phone, email, address, tagList,
                policySet, liabilities, commission, assetsSet);

        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
