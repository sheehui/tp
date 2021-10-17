package donnafin.testutil;

import donnafin.logic.PersonAdapter;
import donnafin.logic.commands.CommandTestUtil;
import donnafin.ui.Ui;
import javafx.stage.Stage;

public class DummyUiForCommands implements Ui {
    private int countHelp = 0;
    private int countHome = 0;
    private int countExit = 0;
    private int countView = 0;

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
    public void showHome() {
        countHome++;
    }

    private boolean isValid(int countHome, int countExit, int countHelp, int countView) {
        return countHome == this.countHome && countExit == this.countExit
            && countHelp == this.countHelp && countView == this.countView;
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
            return isValid(1, 0, 0, 0);
        case SHOW_EXIT:
            return isValid(0, 1, 0, 0);
        case SHOW_HELP:
            return isValid(0, 0, 1, 0);
        case SHOW_VIEW:
            return isValid(0, 0, 0, 1);
        default:
            assert false : "No such Ui Action Type";
            return false;
        }
    }
}
