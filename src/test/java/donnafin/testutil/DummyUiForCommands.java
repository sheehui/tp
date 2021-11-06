package donnafin.testutil;

import java.util.ArrayList;
import java.util.List;

import donnafin.logic.PersonAdapter;
import donnafin.logic.commands.CommandTestUtil;
import donnafin.ui.Ui;
import javafx.stage.Stage;

public class DummyUiForCommands implements Ui {
    private int countHelp = 0;
    private int countHome = 0;
    private int countExit = 0;
    private int countView = 0;
    private final List<ViewFinderState> tabSwitches = new ArrayList<>();
    private ViewFinderState curr = ViewFinderState.CONTACT;

    @Override
    public void start(Stage primaryStage) {}

    @Override
    public void showHelp() {
        countHelp++;
    }

    @Override
    public void beginExit() {
        countExit++;
    }

    @Override
    public void showClientView(PersonAdapter subject) {
        countView++;
    }

    @Override
    public void switchClientViewTab(ViewFinderState tab) {
        tabSwitches.add(tab);
        curr = tab;
    }

    @Override
    public void refreshTab() {
        tabSwitches.add(curr);
    }

    @Override
    public void showHome() {
        countHome++;
    }

    private boolean isValid(
            int countHome, int countExit, int countHelp,
            int countView, List<ViewFinderState> tabSwitches) {
        return countHome == this.countHome && countExit == this.countExit && countHelp == this.countHelp
                && countView == this.countView && tabSwitches.equals(this.tabSwitches);
    }

    /**
     * Check if a {@code Ui} instance would appear to have undergone a given action.
     *
     * @param expectedType Ui action that should be demanded
     * @return whether the expected function was returned.
     */
    public boolean isValid(CommandTestUtil.UiActionType expectedType) {
        switch (expectedType) {
        case SHOW_HOME:
            return isValid(1, 0, 0, 0, List.of());
        case SHOW_EXIT:
            return isValid(0, 1, 0, 0, List.of());
        case SHOW_HELP:
            return isValid(0, 0, 1, 0, List.of());
        case SHOW_VIEW:
            return isValid(0, 0, 0, 1, List.of());
        case SWITCH_TAB_CONTACT:
            return isValid(0, 0, 0, 0, List.of(ViewFinderState.CONTACT));
        case SWITCH_TAB_POLICIES:
            return isValid(0, 0, 0, 0, List.of(ViewFinderState.POLICIES));
        case SWITCH_TAB_ASSETS:
            return isValid(0, 0, 0, 0, List.of(ViewFinderState.ASSETS));
        case SWITCH_TAB_LIABILITIES:
            return isValid(0, 0, 0, 0, List.of(ViewFinderState.LIABILITIES));
        case SWITCH_TAB_NOTES:
            return isValid(0, 0, 0, 0, List.of(ViewFinderState.NOTES));
        default:
            assert false : "No such Ui Action Type";
            return false;
        }
    }

    @Override
    public ViewFinderState getUiState() {
        return ViewFinderState.CONTACT;
    }
}
