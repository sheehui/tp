package donnafin.ui;

import donnafin.logic.InvalidFieldException;
import donnafin.logic.PersonAdapter;
import donnafin.logic.PersonAdapter.PersonField;
import donnafin.logic.commands.exceptions.CommandException;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.model.person.Asset;
import donnafin.model.person.Attribute;
import donnafin.model.person.Liability;
import donnafin.model.person.Policy;
import donnafin.ui.CommandBox.CommandExecutor;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class ClientInfoPanel extends UiPart<Region> {

    private static final String FXML = "ClientInfoPanel.fxml";
    private final PersonAdapter personAdapter;
    private final CommandExecutor commandExecutor;

    @FXML
    private AnchorPane root;

    @FXML
    private Button contact;

    @FXML
    private Button policies;

    @FXML
    private Button assets;

    @FXML
    private Button liabilities;

    @FXML
    private Button notes;

    @FXML
    private VBox attributeDisplayContainer;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public ClientInfoPanel(PersonAdapter personAdapter, CommandExecutor commandExecutor) {
        super(FXML);
        this.personAdapter = personAdapter;
        this.commandExecutor = commandExecutor;
        changeTabToContact();
    }

    private AttributePanel createAttributePanel(Attribute attr) {
        String fieldInString = attr.getClass().getSimpleName();
        return new AttributePanel(
                fieldInString,
                attr.toString(),
                createEditHandler(getPersonContactField(fieldInString))
        );
    }

    /** Gets the PersonField enum type of attribute from label */
    private PersonField getPersonContactField(String fieldInString) {
        switch(fieldInString) {
        case "Name":
            return PersonField.NAME;
        case "Address":
            return PersonField.ADDRESS;
        case "Phone":
            return PersonField.PHONE;
        case "Email":
            return PersonField.EMAIL;
        default:
            throw new IllegalArgumentException("Unexpected Person Field used");
        }
    }

    private AttributePanel.EditHandler createEditHandler(PersonField field) {
        return newValue -> {
            try {
                this.personAdapter.edit(field, newValue);
                return null;
            } catch (InvalidFieldException e) {
                return e.getMessage();
            }
        };
    }

    /**
     * Updates the VBox content to the Client's contact Details
     */
    public void changeTabToContact() {
        refresh();
        personAdapter.getContactAttributesList().stream()
                .map(attr -> createAttributePanel(attr).getRoot())
                .forEach(y -> attributeDisplayContainer.getChildren().add(y));
    }

    /** Gets the {@code CommandExecutor} to carry out switching to contact command */
    public void makeSwitchTabContactCommand() throws CommandException, ParseException {
        commandExecutor.execute("switch contact");
    };

    /** Gets the {@code CommandExecutor} to carry out switching to policies command */
    public void makeSwitchTabPoliciesCommand() throws CommandException, ParseException {
        commandExecutor.execute("switch policies");
    };

    /** Gets the {@code CommandExecutor} to carry out switching to assets command */
    public void makeSwitchTabAssetsCommand() throws CommandException, ParseException {
        commandExecutor.execute("switch assets");
    };

    /** Gets the {@code CommandExecutor} to carry out switching to notes command */
    public void makeSwitchTabNotesCommand() throws CommandException, ParseException {
        commandExecutor.execute("switch notes");
    };

    /** Gets the {@code CommandExecutor} to carry out switching to liabilities command */
    public void makeSwitchTabLiabilitiesCommand() throws CommandException, ParseException {
        commandExecutor.execute("switch liabilities");
    };

    protected void changeTabToPolicies() {
        refresh();
        attributeDisplayContainer.getChildren().add(
                new AttributeTable<>(
                        Policy.TABLE_CONFIG, personAdapter.getSubject().getPolicies()
                ).getRoot()
        );
    }

    protected void changeTabToAssets() {
        refresh();
        attributeDisplayContainer.getChildren().add(
                new AttributeTable<>(
                        Asset.TABLE_CONFIG, personAdapter.getSubject().getAssets()
                ).getRoot()
        );
    }

    protected void changeTabToLiabilities() {
        refresh();
        attributeDisplayContainer.getChildren().add(
                new AttributeTable<>(
                        Liability.TABLE_CONFIG, personAdapter.getSubject().getLiabilities()
                ).getRoot()
        );
    }

    protected void changeTabToNotes() {
        refresh();
        TextArea notesField = new TextArea();
        notesField.setText(personAdapter.getSubject().getNotes().getNotes());
        notesField.textProperty().addListener((observableValue, olNotes, newNotes) -> {
            // TODO: Replace this whole listener with just calling an edit command.
            // Any errors should be raised in the command box, after execution of the
            // edit notes logic in Command (See how the buttons on press are handled).
            try {
                personAdapter.edit(PersonField.NOTES, newNotes);
            } catch (InvalidFieldException e) {
                assert false : "Editing Notes failed ACCEPT-ALL validation";
            }
        });
        attributeDisplayContainer.getChildren().add(notesField);
    }

    private void refresh() {
        attributeDisplayContainer.getChildren().clear();
    }

}
