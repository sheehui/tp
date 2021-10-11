package donnafin.ui;

import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import donnafin.logic.InvalidFieldException;
import donnafin.logic.PersonAdapter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

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
        ObservableList<AttributePanel> attributePanelObservableList = makeAttributePanelList();
        clientInfoList.setItems(attributePanelObservableList);
        clientInfoList.setCellFactory(listView -> new AttributeListViewCell());
    }

    private ObservableList<AttributePanel> makeAttributePanelList() {
        BiConsumer<PersonAdapter.PersonField, String> editor = (x, y) -> {
            try {
                personAdapter.edit(x, y);
            } catch (InvalidFieldException e) {
                e.printStackTrace();
            }
        };

        ObservableList<AttributePanel> attributePanelObservableList =
                personAdapter.getAllAttributesList().stream()
                        .map(x -> new AttributePanel(x, editor))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));

        return attributePanelObservableList;
    }

    //    /**
    //     * Set PersonAdapter in Attribute panel.
    //     */
    //    private void setAttributePersonAdapter() {
    //        try {
    //            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AttributePanel.fxml"));
    //            Parent root = loader.load();
    //            AttributePanel attributePanelController = loader.getController();
    //            attributePanelController.setPersonAdapter(personAdapter);
    //        } catch (IOException e) {
    //            System.out.println(e.getMessage());
    //        }
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
