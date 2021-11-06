package donnafin.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

/**
 * A UI component that displays information of a {@code Person}.
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
    private Label valueLabel;

    @FXML
    private AnchorPane anchorPane;

    /**
     * Constructor for Attribute panel
     *
     * @param fieldInString field heading.
     * @param value initial value of the attribute.
     */
    public AttributePanel(String fieldInString, String value) {
        super(FXML);
        fieldLabel.setText(fieldInString);
        valueLabel.setText(value);
    }
}
