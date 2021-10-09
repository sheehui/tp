package seedu.address.ui;

import java.util.Objects;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.model.person.Attribute;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class AttributePanel extends UiPart<Region> implements Attribute {

    private static final String FXML = "AttributePanel.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private Label label;

    @FXML
    private TextField textField;

    private Attribute attribute;

    private String packagedExtraField = "seedu.address.model.person.";

    /**
     * Constructor for Attribute panel
     * @param attribute
     */
    public AttributePanel(Attribute attribute) {
        super(FXML);
        this.attribute = attribute;
        String attributeName = attribute.getClass().getName().replace(packagedExtraField, "");
        label.setText(attributeName);
        textField.setText(attribute.toString());
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
                && Objects.equals(label, attributePanel.label)
                && Objects.equals(textField, attributePanel.textField);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attribute, label, textField);
    }

    public void setLabel(String text) {
        label.setText(text);
    }

    public String getLabelText() {
        return this.label.getText();
    }

    public void setTextField(String text) {
        textField.setText(text);
    }

    public String getTextFieldString() {
        return this.textField.getText();
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        String newTextField = textField.getText();
        System.out.println(attribute.toString());
    }

}

