package donnafin.ui;

import donnafin.logic.PersonAdapter;
import donnafin.logic.parser.exceptions.ParseException;
import javafx.stage.Stage;

/**
 * API of UI component
 */
public interface Ui {

    enum ViewFinderState {
        PERSON_LIST_PANEL,
        CONTACT,
        POLICIES,
        ASSETS,
        LIABILITIES,
        NOTES
    }

    /** Starts the UI (and the App).  */
    void start(Stage primaryStage);

    void showHelp();

    void beginExit();

    void showClientView(PersonAdapter subject);

    void switchClientViewTab(ViewFinderState tab) throws ParseException;

    void showHome();

    ViewFinderState getUiState();
}
