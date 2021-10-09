package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Consumer;

import seedu.address.model.Model;
import seedu.address.ui.Ui;

/**
 * Lists all persons in the address book to the user.
 */
public class HomeCommand extends Command {

    public static final String COMMAND_WORD = "home";

    public static final String MESSAGE_SUCCESS = "Currently viewing home";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Consumer<Ui> uiAction = ui -> {
            ui.showHome();
        };
        return new CommandResult(MESSAGE_SUCCESS, uiAction);
    }
}
