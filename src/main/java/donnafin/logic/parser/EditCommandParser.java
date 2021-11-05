package donnafin.logic.parser;

import static donnafin.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static donnafin.logic.parser.CliSyntax.PREFIX_EMAIL;
import static donnafin.logic.parser.CliSyntax.PREFIX_NAME;
import static donnafin.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import donnafin.logic.PersonAdapter;
import donnafin.logic.commands.EditCommand;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.model.person.Address;
import donnafin.model.person.Email;
import donnafin.model.person.Name;
import donnafin.model.person.Phone;


public class EditCommandParser implements Parser<EditCommand> {

    private final PersonAdapter personAdapter;

    public EditCommandParser(PersonAdapter personAdapter) {
        this.personAdapter = personAdapter;
    }

    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @param args user input indicating field to be edited and new value.
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public EditCommand parse(String args) throws ParseException {
        Objects.requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);
        Function<PersonAdapter, PersonAdapter> fn = pa -> pa;
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            fn = fn.compose(pa -> {
                pa.edit(name);
                return pa;
            });
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
            fn = fn.compose(pa -> {
                pa.edit(phone);
                return pa;
            });
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
            fn = fn.compose(pa -> {
                pa.edit(email);
                return pa;
            });
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
            fn = fn.compose(pa -> {
                pa.edit(address);
                return pa;
            });
        }

        // Checks if at least one prefix is present
        if (List.of(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS).stream().allMatch(
            prefix -> argMultimap.getValue(prefix).isEmpty())
        ) {
            throw new ParseException("Please enter a valid prefix");
        }

        return new EditCommand(personAdapter, fn::apply);
    }
}
