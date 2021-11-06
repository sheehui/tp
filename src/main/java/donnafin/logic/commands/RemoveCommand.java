package donnafin.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import donnafin.commons.core.types.Index;
import donnafin.logic.PersonAdapter;
import donnafin.logic.PersonAdapter.PersonField;
import donnafin.logic.commands.exceptions.CommandException;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.model.Model;
import donnafin.model.person.Asset;
import donnafin.model.person.Liability;
import donnafin.model.person.Policy;
import donnafin.ui.Ui;

public class RemoveCommand extends Command {

    public static final String COMMAND_WORD = "remove";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": removes a row from the attribute table. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: remove 3";
    public static final String MESSAGE_SUCCESS = "Successfully removed row.";
    public static final String MESSAGE_INVALID_TAB = "Wrong tab for RemoveCommand.";

    private final PersonAdapter personAdapter;
    private final PersonField field;
    private final Index index;

    /** Constructor for {@code RemoveCommand}. */
    public RemoveCommand(PersonAdapter personAdapter, PersonField field, Index index) throws ParseException {
        this.personAdapter = personAdapter;
        this.field = field;
        this.index = index;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        switch (field) {
        case POLICIES:
            Set<Policy> newPolicies = removeWithOrder(personAdapter.getSubject().getPolicies(), index);
            personAdapter.editPolicies(newPolicies);
            break;
        case LIABILITIES:
            Set<Liability> newLiabilities = removeWithOrder(personAdapter.getSubject().getLiabilities(), index);
            personAdapter.editLiabilities(newLiabilities);
            break;
        case ASSETS:
            Set<Asset> newAssets = removeWithOrder(personAdapter.getSubject().getAssets(), index);
            personAdapter.editAssets(newAssets);
            break;
        default:
            throw new CommandException(MESSAGE_INVALID_TAB);
        }

        return new CommandResult(MESSAGE_SUCCESS, Ui::refreshTab);
    }

    private <T> Set<T> removeWithOrder(Set<T> original, Index index) throws CommandException {
        List<T> newSet = original.stream()
            .sorted(Comparator.comparing(Object::toString))
            .collect(Collectors.toList());
        try {
            newSet.remove(index.getZeroBased()); // can throw IndexOutOfBoundsException
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException("No such index found.");
        }
        return new HashSet<>(newSet);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RemoveCommand)) {
            return false;
        }
        RemoveCommand that = (RemoveCommand) o;
        return personAdapter.equals(that.personAdapter) && field == that.field && index.equals(that.index);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personAdapter, field, index);
    }
}
