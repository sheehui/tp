package donnafin.logic.commands;

import static donnafin.logic.commands.CommandTestUtil.assertCommandAction;
import static donnafin.logic.commands.CommandTestUtil.assertCommandSuccess;
import static donnafin.logic.commands.HomeCommand.MESSAGE_SUCCESS;

import org.junit.jupiter.api.Test;

import donnafin.model.AddressBook;
import donnafin.model.Model;
import donnafin.model.ModelManager;
import donnafin.model.UserPrefs;
import donnafin.ui.Ui;


public class HomeCommandTest {
    private final Model model = new ModelManager(new AddressBook(), new UserPrefs(), null);
    private final Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs(), null);

    @Test
    public void execute_validCommand_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, Ui::showHome);
        assertCommandSuccess(new HomeCommand(), model, expectedCommandResult, expectedModel);
        assertCommandAction(new HomeCommand(), CommandTestUtil.UiActionType.SHOW_HOME);
    }
}

