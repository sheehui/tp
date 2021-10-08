package seedu.address.ui;

import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.logic.PersonAdapter;

public class ClientInfoPanel extends UiPart<Region> {
    private static final String FXML = "ClientInfoPanel.fxml";
    private PersonAdapter personAdapter;

    @FXML
    private ListView<AttributePanel> clientInfoList;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public ClientInfoPanel(PersonAdapter personAdapter) {
        super(FXML);
        this.personAdapter = personAdapter;
        // Make all the attributes into FXML AttributePanel
        ObservableList<AttributePanel> attributePanelObservableList =
                personAdapter.getAllAttributesList().stream()
                        .map(x -> new AttributePanel(x))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));
        clientInfoList.setItems(attributePanelObservableList);
        clientInfoList.setCellFactory(listView -> new AttributeListViewCell());
    }

    class AttributeListViewCell extends ListCell<AttributePanel> {
        @Override
        protected void updateItem(AttributePanel attribute, boolean empty) {
            super.updateItem(attribute, empty);

            if (empty || attribute == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(attribute.getRoot());
            }
        }
    }


    /**
     * Handles the Enter button pressed event.
     */
    private void handleCommandEntered(String attributeChanged) {
        System.out.println(attributeChanged);
    }
}
