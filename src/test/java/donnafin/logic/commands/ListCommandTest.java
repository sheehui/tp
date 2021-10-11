package donnafin.logic.commands;

import static donnafin.logic.commands.CommandTestUtil.assertCommandSuccess;
import static donnafin.logic.commands.CommandTestUtil.showPersonAtIndex;
import static donnafin.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static donnafin.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import donnafin.model.Model;
import donnafin.model.ModelManager;
import donnafin.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), null);
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), null);
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
