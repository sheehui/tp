package seedu.address.logic.commands;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.PersonAdapter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

import java.util.List;

public class ViewCommand extends Command{

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views a contact card with more detail.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_VIEW_PERSON_SUCCESS = "Viewing Person: %1$s";

    public static final String VIEW_COMMAND_ERROR =
            "Index inputted is invalid!";

    public static final String MESSAGE_ARGUMENT = "Index: %1$d";

    private final Index index;

    public ViewCommand(Index index) {
        requireAllNonNull(index);
        this.index = index;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person person= lastShownList.get(index.getZeroBased());
        PersonAdapter personToView = new PersonAdapter(model, person);
        //TODO: Please do your magic Tee Chin
        return new CommandResult(String.format(MESSAGE_VIEW_PERSON_SUCCESS, person.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewCommand // instanceof handles nulls
                && index.equals(((ViewCommand) other).index)); // state check
    }
}
