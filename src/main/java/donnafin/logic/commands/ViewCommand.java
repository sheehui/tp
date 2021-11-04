package donnafin.logic.commands;

import static donnafin.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.List;

import donnafin.commons.core.Messages;
import donnafin.commons.core.types.Index;
import donnafin.logic.PersonAdapter;
import donnafin.logic.commands.exceptions.CommandException;
import donnafin.logic.parser.ContactTabParser;
import donnafin.model.Model;
import donnafin.model.person.Person;

public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views a client in more detail.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_VIEW_PERSON_SUCCESS = "Viewing Client: %1$s";

    private final Index index;

    /**
     * The Constructor for the view command.
     * @param index index of the person that you want to view.
     */
    public ViewCommand(Index index) {
        requireAllNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_INDEX);
        }
        Person person = lastShownList.get(index.getZeroBased());
        PersonAdapter personToView = new PersonAdapter(model, person);
        String feedbackToUser = String.format(MESSAGE_VIEW_PERSON_SUCCESS, person.getName());
        return new CommandResult(
            feedbackToUser,
            ui -> {
                ui.showClientView(personToView);
            },
            logic -> {
                logic.setParserStrategy(new ContactTabParser(personToView));
            });
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewCommand // instanceof handles nulls
                && index.equals(((ViewCommand) other).index)); // state check
    }
}
