package donnafin.ui;

import java.util.function.BiConsumer;

import donnafin.logic.PersonAdapter;
import donnafin.model.person.Attribute;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class AttributePanel extends UiPart<Region> implements Attribute {

    private static final String FXML = "AttributePanel.fxml";
    private String field;
    private String value;

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

    private final Attribute attribute;

    private BiConsumer<PersonAdapter.PersonField, String> editor;
    private STATE state;

    enum STATE {
        EDIT_MODE,
        VIEW_MODE
    }

    /**
     * Constructor for Attribute panel
     * @param attribute
     */
    public AttributePanel(Attribute attribute, BiConsumer<PersonAdapter.PersonField, String> editor) {
        super(FXML);
        this.attribute = attribute;
        this.editor = editor;
        this.field = attribute.getClass().getSimpleName();
        this.value = attribute.toString();

        fieldLabel.setText(this.field);
        valueLabel.setText(this.value);
        valueTextField.setText(this.value);
        valueTextField.focusedProperty().addListener(
                (ignoreObservable, ignoreOldValue, newValue) -> focusOutline.setVisible(newValue));
        updateView(STATE.VIEW_MODE);
    }

    private void updateView(STATE intended) {
        if (intended == STATE.VIEW_MODE) {
            this.valueTextField.setText(this.value);
            this.valueLabel.setText(this.value);
            this.valueTextField.setOpacity(0);
            this.valueLabel.setOpacity(1);
            this.valueTextField.setEditable(false);
            this.state = STATE.VIEW_MODE;
        } else if (intended == STATE.EDIT_MODE) {
            this.valueTextField.setText(this.value);
            this.valueLabel.setText(this.value);
            this.valueTextField.setOpacity(1);
            this.valueLabel.setOpacity(0);
            this.valueTextField.setEditable(true);
            this.state = STATE.EDIT_MODE;
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
        if (this.state == STATE.EDIT_MODE) {
            this.value = valueTextField.getText();
            editor.accept(getPersonField(), this.value);
            updateView(STATE.VIEW_MODE);
        } else if (this.state == STATE.VIEW_MODE) {
            updateView(STATE.EDIT_MODE);
        } else {
            assert false : "Attribute Panel in an unexpected state";
        }
    }

    /**
     * Gets the PersonField enum type of attribute from label
     * @return Enum PersonField value of attribute
     */
    private PersonAdapter.PersonField getPersonField() {
        switch(this.field) {
        case "Name":
            return PersonAdapter.PersonField.NAME;
        case "Address":
            return PersonAdapter.PersonField.ADDRESS;
        case "Phone":
            return PersonAdapter.PersonField.PHONE;
        case "Email":
            return PersonAdapter.PersonField.EMAIL;
        default:
            return null;
        }
    }
}
