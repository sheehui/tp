package donnafin.logic.commands;

import static java.util.Objects.requireNonNull;

import donnafin.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "All clients listed!";
    public static final String MESSAGE_NO_CLIENTS_FOUND = "No clients found.";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        int clientCount = model.getAddressBook().getPersonList().size();
        return new CommandResult(clientCount == 0 ? MESSAGE_NO_CLIENTS_FOUND : MESSAGE_SUCCESS);
    }
}
