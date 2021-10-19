package donnafin.logic.commands;

import static donnafin.logic.commands.CommandTestUtil.assertCommandAction;
import static donnafin.logic.commands.CommandTestUtil.assertCommandFailure;
import static donnafin.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import donnafin.commons.core.Messages;
import donnafin.commons.core.types.Index;
import donnafin.model.AddressBook;
import donnafin.model.Model;
import donnafin.model.ModelManager;
import donnafin.model.UserPrefs;
import donnafin.model.person.Person;
import donnafin.testutil.PersonBuilder;

public class ViewCommandTest {

    @Test
    public void execute_view_success() {
        final Model model = new ModelManager(new AddressBook(), new UserPrefs(), null);
        final Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs(), null);
        Person person = new PersonBuilder().build();
        model.addPerson(new PersonBuilder().build());
        expectedModel.addPerson(new PersonBuilder().build());
        String expectedResult = String.format(ViewCommand.MESSAGE_VIEW_PERSON_SUCCESS, person.getName());

        CommandResult expectedCommandResult = new CommandResult(expectedResult);
        assertCommandSuccess(new ViewCommand(Index.fromOneBased(1)), model, expectedCommandResult, expectedModel);
        assertCommandAction(new ViewCommand(Index.fromOneBased(1)), CommandTestUtil.UiActionType.SHOW_VIEW);
    }

    @Test
    public void execute_view_failure() {
        final Model model = new ModelManager(new AddressBook(), new UserPrefs(), null);
        assertCommandFailure(new ViewCommand(Index.fromOneBased(1)), model, Messages.MESSAGE_INVALID_PERSON_INDEX);
    }
}
