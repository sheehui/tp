package donnafin.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class AttributePanel extends UiPart<Region> {

    private static final String FXML = "AttributePanel.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private Label fieldLabel;

    @FXML
    private TextField valueTextField;

    @FXML
    private Label valueLabel;

    @FXML
    private Rectangle focusOutline;

    private final EditHandler editor;
    private State state;
    private String value;

    enum State {
        EDIT_MODE,
        VIEW_MODE
    }

    @FunctionalInterface
    interface EditHandler {
        /**
         * EditHandlers will take in the new value and apply it to the given field.
         * If the edit was successful, it will return null else return the error
         * message thrown when trying to edit the field in this manner.
         *
         * @param newValue the new value to be used in the field.
         * @return error message thrown by {@code PersonAdapter}.
         */
        String applyEdit(String newValue);
    }


    /**
     * Constructor for Attribute panel
     *
     * @param fieldInString field heading.
     * @param value initial value of the attribute.
     * @param editor the callback used to commit changes to model.
     */
    public AttributePanel(String fieldInString, String value, EditHandler editor) {
        super(FXML);
        this.editor = editor;
        this.value = value;

        fieldLabel.setText(fieldInString);
        valueLabel.setText(this.value);
        valueTextField.setText(this.value);
        valueTextField.focusedProperty().addListener((
            ignoreObservable, ignoreOldValue, newValue) -> focusOutline.setVisible(newValue));
        updateView(State.VIEW_MODE);
    }

    private void updateView(State intended) {
        this.valueTextField.setText(this.value);
        this.valueLabel.setText(this.value);

        if (intended == State.VIEW_MODE) {
            this.valueTextField.setOpacity(0);
            this.valueLabel.setOpacity(1);
            this.valueTextField.setEditable(false);
            this.state = State.VIEW_MODE;
        } else if (intended == State.EDIT_MODE) {
            this.valueTextField.setOpacity(1);
            this.valueLabel.setOpacity(0);
            this.valueTextField.setEditable(true);
            this.state = State.EDIT_MODE;
        } else {
            assert false : "Attribute Panel in an unexpected state";
        }
    }

    /**
     * Handles the Enter button pressed event.
     * Updates client info after user edit.
     */
    @FXML
    private void handleCommandEntered() {
        switch (this.state) {
        case EDIT_MODE:
            this.value = valueTextField.getText();
            String errMessage = editor.applyEdit(this.value);
            if (errMessage != null) {
                handleError(errMessage);
            } else {
                updateView(State.VIEW_MODE);
            }
            break;
        case VIEW_MODE:
            updateView(State.EDIT_MODE);
            break;
        default:
            assert false : "Attribute Panel in an unexpected state";
        }
    }

    private void handleError(String errMessage) {
        Alert editFailed = new Alert(Alert.AlertType.ERROR);
        editFailed.setHeaderText("Could not save your edit.");
        editFailed.setContentText(errMessage);
        editFailed.show();
    }
}
