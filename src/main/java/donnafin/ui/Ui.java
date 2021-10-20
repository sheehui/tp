package donnafin.ui;

import donnafin.logic.PersonAdapter;
import javafx.stage.Stage;

/**
 * API of UI component
 */
public interface Ui {

    enum ClientViewTab {
        Contact,
        Policies,
        Assets,
        Liabilities,
        Notes
    }

    /** Starts the UI (and the App).  */
    void start(Stage primaryStage);

    void showHelp();

    void beginExit();

    void showClientView(PersonAdapter subject);

    void switchClientViewTab(ClientViewTab tab);

    void showHome();
}
