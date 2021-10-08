package seedu.address.ui;

import javafx.stage.Stage;
import seedu.address.logic.PersonAdapter;

/**
 * API of UI component
 */
public interface Ui {

    /** Starts the UI (and the App).  */
    void start(Stage primaryStage);

    void showHelp();

    void beginExit();

    void showClientView(PersonAdapter subject);
}
