package donnafin.logic.commands;

import static donnafin.logic.commands.CommandTestUtil.assertCommandAction;
import static donnafin.testutil.TypicalPersons.getTypicalAddressBook;
import static donnafin.testutil.TypicalPersons.getTypicalPersons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import donnafin.logic.PersonAdapter;
import donnafin.model.Model;
import donnafin.model.ModelManager;
import donnafin.model.UserPrefs;
import donnafin.model.person.Person;
import donnafin.ui.Ui;
import donnafin.ui.Ui.ViewFinderState;


public class SwitchTabCommandTest {

    private PersonAdapter personAdapter;
    private Model model;
    private Person person;

    @BeforeEach
    private void reset() {
        person = getTypicalPersons().get(0);
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), null);
        this.personAdapter = new PersonAdapter(model, person);
    }

    @Test
    public void execute_switchTab_success() {
        assertCommandAction(
                new SwitchTabCommand(ViewFinderState.CONTACT, personAdapter),
                CommandTestUtil.UiActionType.SWITCH_TAB_CONTACT);
        assertCommandAction(
                new SwitchTabCommand(ViewFinderState.ASSETS, personAdapter),
                CommandTestUtil.UiActionType.SWITCH_TAB_ASSETS);
        assertCommandAction(
                new SwitchTabCommand(Ui.ViewFinderState.LIABILITIES, personAdapter),
                CommandTestUtil.UiActionType.SWITCH_TAB_LIABILITIES);
        assertCommandAction(
                new SwitchTabCommand(ViewFinderState.POLICIES, personAdapter),
                CommandTestUtil.UiActionType.SWITCH_TAB_POLICIES);
        assertCommandAction(
                new SwitchTabCommand(ViewFinderState.NOTES, personAdapter),
                CommandTestUtil.UiActionType.SWITCH_TAB_NOTES);
    }
}
