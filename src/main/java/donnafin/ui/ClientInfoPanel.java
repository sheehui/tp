package donnafin.ui;

import java.util.function.BiConsumer;

import donnafin.logic.InvalidFieldException;
import donnafin.logic.PersonAdapter;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class ClientInfoPanel extends UiPart<Region> {
    private static final String FXML = "ClientInfoPanel.fxml";
    private PersonAdapter personAdapter;

    @FXML
    private VBox clientInfoList;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public ClientInfoPanel(PersonAdapter personAdapter) {
        super(FXML);
        this.personAdapter = personAdapter;

        BiConsumer<PersonAdapter.PersonField, String> editor = (x, y) -> {
            try {
                personAdapter.edit(x, y);
            } catch (InvalidFieldException e) {
                e.printStackTrace();
            }
        };
        personAdapter.getAllAttributesList().stream()
                .map(x -> new AttributePanel(x, editor).getRoot())
                .forEach(y -> clientInfoList.getChildren().add(y));
    }
}
