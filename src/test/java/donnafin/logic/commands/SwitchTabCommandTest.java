package donnafin.logic.commands;

import static donnafin.logic.commands.CommandTestUtil.assertCommandAction;

import org.junit.jupiter.api.Test;

import donnafin.ui.Ui.ClientViewTab;

public class SwitchTabCommandTest {
    @Test
    public void execute_view_success() {
        assertCommandAction(
                new SwitchTabCommand(ClientViewTab.Contact), CommandTestUtil.UiActionType.SWITCH_TAB_CONTACT);
        assertCommandAction(
                new SwitchTabCommand(ClientViewTab.Assets), CommandTestUtil.UiActionType.SWITCH_TAB_ASSETS);
        assertCommandAction(
                new SwitchTabCommand(ClientViewTab.Liabilities), CommandTestUtil.UiActionType.SWITCH_TAB_LIABILITIES);
        assertCommandAction(
                new SwitchTabCommand(ClientViewTab.Policies), CommandTestUtil.UiActionType.SWITCH_TAB_POLICIES);
        assertCommandAction(
                new SwitchTabCommand(ClientViewTab.Notes), CommandTestUtil.UiActionType.SWITCH_TAB_NOTES);
    }
}
