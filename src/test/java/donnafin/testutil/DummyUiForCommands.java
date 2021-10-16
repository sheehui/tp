package donnafin.testutil;

import donnafin.logic.PersonAdapter;
import donnafin.logic.commands.CommandTestUtil;
import donnafin.ui.Ui;
import javafx.stage.Stage;

public class DummyUiForCommands implements Ui {
    int countHelp = 0;
    int countHome = 0;
    int countExit = 0;
    int countView = 0;

    @Override
    public void start(Stage primaryStage) {}

    @Override
    public void showHelp() { countHelp++; }

    @Override
    public void beginExit() { countExit++; }

    @Override
    public void showClientView(PersonAdapter subject) { countView++; }

    @Override
    public void showHome() { countHome++; }

    public boolean isValid(int countHome, int countExit, int countHelp, int countView) {
        return countHome == this.countHome && countExit == this.countExit
            && countHelp == this.countHelp && countView == this.countView;
    }

    public boolean isValid(CommandTestUtil.UiActionType type) {
        switch (type) {
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
