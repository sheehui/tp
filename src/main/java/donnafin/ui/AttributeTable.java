package donnafin.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class AttributeTable<T> extends UiPart<Region> {

    private static final String FXML = "AttributeTable.fxml";

    /**
     * ColumnConfig is a data class representing the values required to make a
     * column in the {@code AttributeTable}.
     */
    public static class ColumnConfig {
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

    public static class TableConfig<R> {
        final String tableTitle;
        final List<ColumnConfig> columnConfigs;
        final AggregatorLabelCreator<? super R> aggregatorLabelCreator;

        /** Create Table Config for setting up {@code AttributeTable} */
        public TableConfig(
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

    @FXML
    private TableView<T> table;

    private final ObservableList<T> data;

    /**
     * Creates a custom made table for a collection of attributes.
     * Creates a VBox that contains a table heading, a line that shows an
     * aggregation of the collection, followed by a table of the collection.
     *
     * @param tableConfig configurations for setting up this component
     * @param collection the collection of attributes
     */
    public AttributeTable(TableConfig<? super T> tableConfig, Collection<? extends T> collection) {
        super(FXML);
        table.getColumns().clear();
        table.setEditable(true);

        this.title.setText(tableConfig.tableTitle);

        List<TableColumn<T, String>> columns = new ArrayList<>();
        for (ColumnConfig columnConfig : tableConfig.columnConfigs) {
            TableColumn<T, String> col = new TableColumn<>(columnConfig.heading);
            col.setMinWidth(columnConfig.minWidth);
            col.setMaxWidth(600);
            col.setCellValueFactory(new PropertyValueFactory<>(columnConfig.propertyName));
            columns.add(col);
        }

        aggregatorLabel.setText(tableConfig.aggregatorLabelCreator.applyOn(collection));

        List<T> sortedCollection = collection.stream()
                .sorted(Comparator.comparing(Object::toString))
                .collect(Collectors.toList());
        data = FXCollections.observableArrayList(sortedCollection);
        table.getColumns().addAll(columns);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setItems(data);
    }

    public VBox getContainer() {
        return container;
    }
}
