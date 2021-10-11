package donnafin.logic.commands;

import static donnafin.logic.commands.CommandTestUtil.assertCommandSuccess;
import static donnafin.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import donnafin.model.AddressBook;
import donnafin.model.Model;
import donnafin.model.ModelManager;
import donnafin.model.UserPrefs;
import donnafin.ui.Ui;

public class HelpCommandTest {
    private Model model = new ModelManager(new AddressBook(), new UserPrefs(), null);
    private Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs(), null);

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, Ui::showHelp);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }
}
