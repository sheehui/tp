package donnafin.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AttributeTable<T> extends UiPart<Region> {

    static private final String FXML = "AttributeTable.fxml";

    /**
     * ColumnConfig is a data class representing the values required to make a
     * column in the {@code AttributeTable}.
     */
    static protected class ColumnConfig {
        final String heading;
        final String propertyName;
        final int minWidth;

        /**
         * Create a column config for use in the {@code AttributeTable}.
         * Note that {@code 'get' +  propertyName + '()'} should be a getter method
         * in the attribute targeted that returns a String.
         *
         * @param heading title of the column
         * @param propertyName name of the variable (must have a public getter function too)
         * @param minWidth in pixels
         */
        public ColumnConfig(String heading, String propertyName, int minWidth) {
            this.heading = heading;
            this.propertyName = propertyName;
            this.minWidth = minWidth;
        }
    }

    @FunctionalInterface
    public interface AggregatorLabelCreator<R> {
        /**
         * Convert a collection of attributes (rows) into a string for
         * displaying an aggregate value.
         *
         * @param collection of the attributes
         * @return String output that should show in the Aggregate Label.
         */
        String applyOn(Collection<? extends R> collection);
    }

    static protected class TableConfig<R> {
        final String tableTitle;
        final List<ColumnConfig> columnConfigs;
        final AggregatorLabelCreator<? super R> aggregatorLabelCreator;

        TableConfig(
                String tableTitle,
                List<ColumnConfig> columnConfigs,
                AggregatorLabelCreator<? super R> aggregatorLabelCreator) {
            this.columnConfigs = columnConfigs;
            this.aggregatorLabelCreator = aggregatorLabelCreator;
            this.tableTitle = tableTitle;
        }
    }

    @FXML
    private VBox container;

    @FXML
    private Label title;

    @FXML
    private Label aggregatorLabel;

    private final TableView<T> table;
    private final ObservableList<T> data;

    public AttributeTable(TableConfig<? super T> tableConfig, Collection<? extends T> collection) {
        super(FXML);
        table = new TableView<>();
        table.setEditable(true);

        this.title.setText(tableConfig.tableTitle);

        List<TableColumn<T, String>> columns = new ArrayList<>();
        for (ColumnConfig columnConfig : tableConfig.columnConfigs) {
            TableColumn<T, String> col = new TableColumn<>(columnConfig.heading);
            col.setMinWidth(columnConfig.minWidth);
            col.setCellValueFactory(new PropertyValueFactory<>(columnConfig.propertyName));
            columns.add(col);
        }

        aggregatorLabel.setText(tableConfig.aggregatorLabelCreator.applyOn(collection));

        data = FXCollections.observableArrayList(collection);
        table.getColumns().addAll(columns);
        table.setItems(data);

        container.getChildren().add(table);
    }

    public VBox getContainer() {
        return container;
    }
}
