package donnafin.logic.commands;

import static donnafin.logic.commands.CommandTestUtil.assertCommandSuccess;
import static donnafin.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.jupiter.api.Test;

import donnafin.model.AddressBook;
import donnafin.model.Model;
import donnafin.model.ModelManager;
import donnafin.model.UserPrefs;
import donnafin.ui.Ui;

public class ExitCommandTest {
    private final Model model = new ModelManager(new AddressBook(), new UserPrefs(), null);
    private final Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs(), null);

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, Ui::beginExit);
        assertCommandSuccess(new ExitCommand(), model, expectedCommandResult, expectedModel);
    }
}
