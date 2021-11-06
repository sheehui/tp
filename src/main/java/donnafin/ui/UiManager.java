package donnafin.ui;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import donnafin.MainApp;
import donnafin.commons.core.GuiSettings;
import donnafin.commons.core.LogsCenter;
import donnafin.commons.util.StringUtil;
import donnafin.logic.Logic;
import donnafin.logic.PersonAdapter;
import donnafin.logic.commands.CommandResult;
import donnafin.logic.commands.exceptions.CommandException;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.model.person.Person;
import donnafin.ui.state.UiState;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * The manager of the UI component.
 */
public class UiManager implements Ui {

    public static final String ALERT_DIALOG_PANE_FIELD_ID = "alertDialogPane";

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/DonnaFin.png";

    private final Logic logic;
    private MainWindow mainWindow;
    private UiState uiState;

    /**
     * Creates a {@code UiManager} with the given {@code Logic}.
     */
    public UiManager(Logic logic) {
        super();
        this.logic = logic;
        this.uiState = new UiState();
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");

        //Set the application icon.
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        try {
            mainWindow = new MainWindow(primaryStage, this);
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillInnerParts();
        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    @Override
    public void showHelp() {
        this.mainWindow.handleHelp();
    }

    @Override
    public void beginExit() {
        this.mainWindow.handleExit();
    }

    @Override
    public void showClientView(PersonAdapter subject) {
        mainWindow.showClientView(subject);
        uiState.setStatePersonalInformationTab();
    }

    @Override
    public void switchClientViewTab(ViewFinderState tab) {
        mainWindow.switchClientViewTab(tab);
        switch (tab) {
        case CONTACT:
            uiState.setStatePersonalInformationTab();
            break;
        case POLICIES:
            uiState.setStatePoliciesTab();
            break;
        case ASSETS:
            uiState.setStateFinancialInformationTab();
            break;
        case LIABILITIES:
            uiState.setStateLiabilitiesTab();
            break;
        case NOTES:
            uiState.setStateNotesTab();
            break;
        default:
            assert false : "Impossible enum state";
        }
    }

    @Override
    public void refreshTab() {
        this.switchClientViewTab(this.getUiState());
    }


    /**
     * Changes the {@code mainWindow} to {@code personList} panel, aka "Home"
     */
    public void showHome() {
        mainWindow.switchToHome();
        uiState.setStatePersonListPanel();
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    /**
     * Shows an alert dialog on {@code owner} with the given parameters.
     * This method only returns after the user has closed the alert dialog.
     */
    void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(mainWindow.getPrimaryStage(), type, title, headerText, contentText);
    }

    private static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
                                               String contentText) {
        final Alert alert = new Alert(type);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.getDialogPane().setId(ALERT_DIALOG_PANE_FIELD_ID);
        alert.showAndWait();
    }

    /**
     * Shows an error alert dialog with {@code title} and error message, {@code e},
     * and exits the application after the user has closed the alert dialog.
     */
    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        logger.severe(title + " " + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }

    public GuiSettings getGuiSettings() {
        return logic.getGuiSettings();
    }

    public ObservableList<Person> getFilteredPersonList() {
        return logic.getFilteredPersonList();
    }

    public Path getAddressBookFilePath() {
        return logic.getAddressBookFilePath();
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        logic.setGuiSettings(guiSettings);
    }

    public ViewFinderState getUiState() {
        return uiState.getState();
    }

    /**
     * Passes the string to logic to execute. Gets the command result back from logic, along with any
     * uiConsumer. If there is one, apply it to the ui
     * @param commandText String entered by the user
     * @return  result of the command of class Command result
     */
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        CommandResult commandResult = logic.execute(commandText);
        Consumer<Ui> uiAction = commandResult.getUiAction();
        assert uiAction != null : "commandResult.uiAction was set as null";
        uiAction.accept(this);
        return commandResult;
    }

    //For testing purposes. See UiManager Test for more info
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UiManager)) {
            return false;
        }
        UiManager uiManager = (UiManager) o;
        return Objects.equals(logic, uiManager.logic) && Objects.equals(mainWindow, uiManager.mainWindow);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logic, mainWindow);
    }
}
