package donnafin.ui;

import donnafin.logic.PersonAdapter;
import javafx.stage.Stage;

/**
 * API of UI component
 */
public interface Ui {

    /** Starts the UI (and the App).  */
    void start(Stage primaryStage);

    void showHelp();

    void beginExit();

    void showClientView(PersonAdapter subject);

    void showHome();
}
