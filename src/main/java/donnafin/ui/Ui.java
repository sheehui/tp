package donnafin.ui;

import donnafin.logic.PersonAdapter;
import donnafin.logic.parser.exceptions.ParseException;
import javafx.scene.control.Alert;
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

    /**
     * Shows an alert dialog on {@code owner} with the given parameters.
     * This method only returns after the user has closed the alert dialog.
     */
    void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText);
}
