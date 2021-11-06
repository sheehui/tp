//@@author Bluntsord
package donnafin.ui;

import java.util.logging.Logger;

import donnafin.commons.core.LogsCenter;
import donnafin.logic.PersonAdapter;
import donnafin.logic.commands.exceptions.CommandException;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.model.person.Asset;
import donnafin.model.person.Attribute;
import donnafin.model.person.Liability;
import donnafin.model.person.Notes;
import donnafin.model.person.Policy;
import donnafin.ui.CommandBox.CommandExecutor;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class ClientPanel extends UiPart<Region> {

    private static final String FXML = "ClientInfoPanel.fxml";
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
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
    public ClientPanel(PersonAdapter personAdapter, CommandExecutor commandExecutor) {
        super(FXML);
        this.personAdapter = personAdapter;
        this.commandExecutor = commandExecutor;
        changeTabToContact();
    }

    private AttributePanel createAttributePanel(Attribute attr) {
        String fieldInString = attr.getClass().getSimpleName();
        return new AttributePanel(fieldInString, attr.toString());
    }

    //@@author mrmrinal
    /** Updates the VBox content to the Client's contact Details */
    public void changeTabToContact() {
        refresh();
        personAdapter.getContactAttributesList().stream()
                .map(attr -> createAttributePanel(attr).getRoot())
                .forEach(y -> attributeDisplayContainer.getChildren().add(y));
        logger.info("Change tab in ClientPanel to Contact");
    }

    protected void changeTabToPolicies() {
        changeTab(new AttributeTable<>(Policy.TABLE_CONFIG, personAdapter.getSubject().getPolicies()).getRoot());
        logger.info("Change tab in ClientPanel to Policies");
    }

    protected void changeTabToAssets() {
        changeTab(new AttributeTable<>(Asset.TABLE_CONFIG, personAdapter.getSubject().getAssets()).getRoot());
        logger.info("Change tab in ClientPanel to Assets");
    }

    protected void changeTabToLiabilities() {
        changeTab(new AttributeTable<>(Liability.TABLE_CONFIG, personAdapter.getSubject().getLiabilities()).getRoot());
        logger.info("Change tab in ClientPanel to Liabilities");
    }

    protected void changeTabToNotes() {
        TextArea notesField = new TextArea();
        notesField.setWrapText(true);
        notesField.setText(personAdapter.getSubject().getNotes().getNotes());
        notesField.textProperty().addListener((observableValue, olNotes, newNotes) -> {
            // TODO: Replace this whole listener with just calling an edit command.
            // Any errors should be raised in the command box, after execution of the
            // edit notes logic in Command (See how the buttons on press are handled).
            personAdapter.edit(new Notes(newNotes));
        });
        changeTab(notesField);
    }

    /** Gets the {@code CommandExecutor} to carry out switching to contact command */
    public void makeSwitchTabContactCommand() throws CommandException, ParseException {
        commandExecutor.execute("tab contact");
    }

    /** Gets the {@code CommandExecutor} to carry out switching to policies command */
    public void makeSwitchTabPoliciesCommand() throws CommandException, ParseException {
        commandExecutor.execute("tab policies");
    }

    /** Gets the {@code CommandExecutor} to carry out switching to assets command */
    public void makeSwitchTabAssetsCommand() throws CommandException, ParseException {
        commandExecutor.execute("tab assets");
    }

    /** Gets the {@code CommandExecutor} to carry out switching to notes command */
    public void makeSwitchTabNotesCommand() throws CommandException, ParseException {
        commandExecutor.execute("tab notes");
    }

    /** Gets the {@code CommandExecutor} to carry out switching to liabilities command */
    public void makeSwitchTabLiabilitiesCommand() throws CommandException, ParseException {
        commandExecutor.execute("tab liabilities");
    }

    //@@author bharathcs
    private void refresh() {
        attributeDisplayContainer.getChildren().clear();
    }

    private void changeTab(Node node) {
        Platform.runLater(() -> {
            refresh();
            attributeDisplayContainer.getChildren().add(node);
        });
        VBox.setVgrow(node, Priority.ALWAYS);
    }

}
