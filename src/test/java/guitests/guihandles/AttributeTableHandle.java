package guitests.guihandles;

import javafx.scene.control.TableView;

public class AttributeTableHandle<T> extends NodeHandle<TableView<T>> {

    public static final String TABLE_DISPLAY_ID = "#table";

    public AttributeTableHandle(TableView<T> rootNode) {
        super(rootNode);
    }
}
