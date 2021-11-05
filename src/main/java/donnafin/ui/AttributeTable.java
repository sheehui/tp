package donnafin.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import donnafin.model.person.Attribute;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/** Generate a rich table view for lists of attributes. */
public class AttributeTable<T extends Attribute> extends UiPart<Region> {

    private static final String FXML = "AttributeTable.fxml";

    /**
     * ColumnConfig is a data class representing the values required to make a
     * column in the {@code AttributeTable}. Used in {@code TableConfig}.
     */
    public static class ColumnConfig {
        final String heading;
        final String propertyName;
        final int maxWidth;
        final int prefWidth;

        /**
         * Create a column config for use in the {@code AttributeTable}.
         * Note that {@code 'get' +  propertyName + '()'} should be a getter method
         * in the attribute targeted that returns a String.
         *
         * @param heading title of the column
         * @param propertyName name of the variable (must have a public getter function too)
         */
        public ColumnConfig(String heading, String propertyName, int prefWidth, int maxWidth) {
            this.heading = heading;
            this.propertyName = propertyName;
            this.maxWidth = maxWidth;
            this.prefWidth = prefWidth;
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

    /**
     * TableConfig is a data class representing the values required to make a
     * table in {@code AttributeTable}.
     */
    public static class TableConfig<R> {
        public final String tableTitle;
        public final List<ColumnConfig> columnConfigs;
        public final AggregatorLabelCreator<? super R> aggregatorLabelCreator;

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

        //@@author bharathcs-reused
        //Reused from https://stackoverflow.com/a/31213320/4179939 with minor modifications.
        TableColumn<T, String> indexCol = new TableColumn<>("");
        indexCol.setCellFactory(col -> {
            TableCell<T, String> cell = new TableCell<>();
            cell.textProperty().bind(Bindings.createStringBinding(() -> {
                if (cell.isEmpty()) {
                    return null;
                } else {
                    return Integer.toString(cell.getIndex() + 1);
                }
            }, cell.emptyProperty(), cell.indexProperty()));
            return cell;
        });
        //@@author

        List<TableColumn<T, String>> columns = new ArrayList<>();
        indexCol.setMinWidth(40);
        indexCol.setMaxWidth(40);
        columns.add(indexCol);

        for (ColumnConfig columnConfig : tableConfig.columnConfigs) {
            TableColumn<T, String> col = new TableColumn<>(columnConfig.heading);
            col.setPrefWidth(columnConfig.prefWidth);
            col.setMaxWidth(columnConfig.maxWidth);
            col.setCellValueFactory(new PropertyValueFactory<>(columnConfig.propertyName));
            col.setCellFactory(tc -> {
                TableCell<T, String> cell = new TableCell<>();
                Text text = new Text();
                cell.setGraphic(text);
                cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
                text.wrappingWidthProperty().bind(col.widthProperty());
                text.textProperty().bind(cell.itemProperty());
                text.setFill(Color.WHITE);
                return cell;
            });
            columns.add(col);
        }
        columns.forEach(col -> col.setSortable(false));

        aggregatorLabel.setText(tableConfig.aggregatorLabelCreator.applyOn(collection));

        List<T> sortedCollection = collection.stream()
                .sorted(Comparator.comparing(Object::toString))
                .collect(Collectors.toList());
        ObservableList<T> data = FXCollections.observableArrayList(sortedCollection);
        table.getColumns().addAll(columns);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setItems(data);
    }
}
