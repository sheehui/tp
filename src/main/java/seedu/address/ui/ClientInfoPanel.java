package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.PersonAdapter;
import seedu.address.model.person.Attributes;

import javafx.scene.layout.Region;

import javax.print.attribute.Attribute;
import java.util.Collections;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ClientInfoPanel extends UiPart<Region> {
    private static final String FXML = "ClientInfoPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ClientInfoPanel.class);
    private PersonAdapter personAdapter;

    @FXML
    private VBox attributesListView = new VBox();

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public ClientInfoPanel(PersonAdapter personAdapter) {
        super(FXML);
        this.personAdapter = personAdapter;
        // Make all the attributes into FXML AttributePanel
        ObservableList<AttributePanel> attributePanelObservableList =
                personAdapter.getAllAttributesList().stream().map(x -> new AttributePanel(x)).
                        collect(Collectors.toCollection(FXCollections::observableArrayList));
        for (AttributePanel attributePanel : attributePanelObservableList) {
            attributesListView.getChildren().add(attributePanel.getRoot());
        };
//        attributesListView.setCellFactory(listView -> new AttributeListViewCell());
    }

    class AttributeListViewCell extends ListCell<Attributes> {
        @Override
        protected void updateItem(Attributes attribute, boolean empty) {
            super.updateItem(attribute, empty);

            if (empty || attribute == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new AttributePanel(attribute).getRoot());
            }
        }
    }
}
