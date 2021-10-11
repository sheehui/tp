package donnafin.ui;

import java.util.Objects;
import java.util.function.BiConsumer;

import donnafin.logic.PersonAdapter;
import donnafin.model.person.Attribute;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

    private final String packagedExtraField = "donnafin.model.person.";

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
        String attributeName = attribute.getClass().getSimpleName();
        String attributeValue = attribute.toString();
        fieldLabel.setText(attributeName);
        valueLabel.setText(attributeValue);
        valueTextField.setText(attributeValue);
        valueTextField.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            /**
             * Called when the value of an {@link ObservableValue} changes.
             * <p>
             * In general, it is considered bad practice to modify the observed value in
             * this method.
             *
             * @param observable The {@code ObservableValue} which value changed
             * @param oldValue   The old value
             * @param newValue
             */
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                focusOutline.setVisible(newValue);
            }
        });

        this.state = STATE.EDIT_MODE;
        handleCommandEntered();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AttributePanel attributePanel = (AttributePanel) o;
        return Objects.equals(attributePanel, attributePanel)
                && Objects.equals(fieldLabel, attributePanel.fieldLabel)
                && Objects.equals(valueTextField, attributePanel.valueTextField)
                && Objects.equals(valueLabel, attributePanel.valueLabel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attribute, fieldLabel, valueTextField);
    }

    public void setFieldLabel(String text) {
        fieldLabel.setText(text);
    }

    public String getFieldLabel() {
        return this.fieldLabel.getText();
    }

    public void setValue(String text) {
        valueTextField.setText(text);
        valueLabel.setText(text);
    }

    public String getValue() {
        return this.valueTextField.getText();
    }

    /**
     * Handles the Enter button pressed event.
     * Updates client info after user edit.
     */
    @FXML
    private void handleCommandEntered() {
        String newTextField = valueTextField.getText();
        System.out.println(getPersonField());
        if (this.state == STATE.EDIT_MODE) {
            this.valueTextField.setOpacity(0);
            this.valueLabel.setText(newTextField);
            this.valueLabel.setOpacity(1);
//            this.valueTextField.setOpacity(1);
//            this.valueLabel.setOpacity(0);
            this.valueTextField.setEditable(false);
            editor.accept(getPersonField(), newTextField);
            this.state = STATE.VIEW_MODE;
        } else if (this.state == STATE.VIEW_MODE) {
            this.valueTextField.setOpacity(1);
            this.valueLabel.setOpacity(0);
            this.valueTextField.setEditable(true);
            this.state = STATE.EDIT_MODE;
        } else {
            assert false : "Attribute Panel in an unexpected state";
        }
    }

    /**
     * Gets the PersonField enum type of attribute from label
     * @return Enum PersonField value of attribute
     */
    private PersonAdapter.PersonField getPersonField() {
        switch(fieldLabel.getText()) {
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
