package donnafin.logic.commands;

import static donnafin.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static donnafin.logic.parser.CliSyntax.PREFIX_EMAIL;
import static donnafin.logic.parser.CliSyntax.PREFIX_NAME;
import static donnafin.logic.parser.CliSyntax.PREFIX_PHONE;
import static java.util.Objects.requireNonNull;

import java.util.Set;

import donnafin.logic.commands.exceptions.CommandException;
import donnafin.model.Model;
import donnafin.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a client to DonnaFin. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 ";


    public static final String MESSAGE_SUCCESS = "New client added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This client already exists in the DonnaFin";
    public static final String MESSAGE_SIMILAR_PERSON = "\nWARNING: Found %d other clients who could be duplicate(s):"
                            + "\n%s\nHint: REMOVE command can be useful to remove duplicates.";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        Set<Person> weakDuplicatesSet = model.getWeakDuplicates(toAdd);
        String feedbackToUser = String.format(MESSAGE_SUCCESS, toAdd);
        if (weakDuplicatesSet != null && weakDuplicatesSet.size() != 0) {
            String listDuplicates = weakDuplicatesSet.stream()
                    .filter(x -> !toAdd.equals(x))
                    .map(p -> p.getName() + "\n")
                    .reduce("", (a, b) -> a + b);
            feedbackToUser += String.format(MESSAGE_SIMILAR_PERSON, weakDuplicatesSet.size(), listDuplicates);
        }

        model.addPerson(toAdd);
        return new CommandResult(feedbackToUser);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd, true));
    }
}
