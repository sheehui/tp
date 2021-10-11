package seedu.address.ui;

import java.io.IOException;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.logic.PersonAdapter;

public class ClientInfoPanel extends UiPart<Region> {
    private static final String FXML = "ClientInfoPanel.fxml";
    private PersonAdapter personAdapter;

    @FXML
    private ListView<AttributePanel> clientInfoList;

//        @FXML
//        private ClientInfoPanel clientInfoPanelController;
    //
    //    private final ObjectProperty<PersonAdapter> adapter = new SimpleObjectProperty<>();

    //    public void initialize() {
    //        adapter.set(personAdapter);
    //        attributePanelController.personAdapterProperty().bind(adapter);
    //    }

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public ClientInfoPanel(PersonAdapter personAdapter) {
        super(FXML);
        this.personAdapter = personAdapter;
        setAttributePersonAdapter();
        // Make all the attributes into FXML AttributePanel
        ObservableList<AttributePanel> attributePanelObservableList =
                personAdapter.getAllAttributesList().stream()
                        .map(x -> new AttributePanel(x))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));
        clientInfoList.setItems(attributePanelObservableList);
        clientInfoList.setCellFactory(listView -> new AttributeListViewCell());
    }

    /**
     * Set PersonAdapter in Attribute panel.
     */
    private void setAttributePersonAdapter() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AttributePanel.fxml"));
            Parent root = (Parent) loader.load();
            AttributePanel attributePanelController = loader.getController();
            attributePanelController.setPersonAdapter(personAdapter);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


//    public PersonAdapter getPersonAdapter() {
//        return personAdapter;
//    }

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
}
