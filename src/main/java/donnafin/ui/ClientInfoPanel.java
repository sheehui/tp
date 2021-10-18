package donnafin.ui;

import java.util.List;

import donnafin.commons.core.types.Money;
import donnafin.logic.InvalidFieldException;
import donnafin.logic.PersonAdapter;
import donnafin.model.person.Asset;
import donnafin.model.person.Attribute;
import donnafin.model.person.Liability;
import donnafin.model.person.Policy;
import donnafin.ui.AttributeTable.ColumnConfig;
import donnafin.ui.AttributeTable.TableConfig;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class ClientInfoPanel extends UiPart<Region> {
    private static final String FXML = "ClientInfoPanel.fxml";
    private static final TableConfig<Policy> policyTableConfig = new TableConfig<>(
        "Policies",
        List.of(
                    new ColumnConfig("Policy Name", "name", 300),
                    new ColumnConfig("Insurer", "insurer", 100),
                    new ColumnConfig("Insured Value", "totalValueInsuredToString", 100),
                    new ColumnConfig("Yearly Premium", "yearlyPremiumsToString", 100),
                    new ColumnConfig("Commission", "commissionToString", 100)
            ),
        policyCol -> {
            Money acc = new Money(0);
            try {
                for (Policy policy : policyCol) {
                    Money commission = policy.getCommission();
                    acc = Money.add(acc, commission);
                }
            } catch (Money.MoneyException e) {
                return "-";
            }
            return acc.toString();
        }
    );
    private static final TableConfig<Asset> assetTableConfig = new TableConfig<>(
        "Assets",
        List.of(
                new ColumnConfig("Asset Name", "name", 300),
                new ColumnConfig("Type", "type", 100),
                new ColumnConfig("Value", "valueToString", 100),
                new ColumnConfig("Remarks", "remarks", 100)
        ),
        assetCol -> {
            Money acc = new Money(0);
            try {
                for (Asset asset : assetCol) {
                    Money commission = asset.getValue();
                    acc = Money.add(acc, commission);
                }
            } catch (Money.MoneyException e) {
                return "-";
            }
            return acc.toString();
        }
    );
    private static final TableConfig<Liability> liabilityTableConfig = new TableConfig<>(
        "Liabilities",
        List.of(
                new ColumnConfig("Liability Name", "name", 300),
                new ColumnConfig("Type", "type", 100),
                new ColumnConfig("Value", "valueToString", 100),
                new ColumnConfig("Remarks", "remarks", 100)
        ),
        liabilityCol -> {
            Money acc = new Money(0);
            try {
                for (Liability liability : liabilityCol) {
                    Money commission = liability.getValue();
                    acc = Money.add(acc, commission);
                }
            } catch (Money.MoneyException e) {
                return "-";
            }
            return acc.toString();
        }
    );

    private final PersonAdapter personAdapter;

    @FXML
    private AnchorPane root;

    @FXML
    private Button personalInformation;

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

    @FXML
    private TextArea notesTextArea;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public ClientInfoPanel(PersonAdapter personAdapter) {
        super(FXML);
        this.personAdapter = personAdapter;
        changeTabToPersonal();
    }

    private AttributePanel createAttributePanel(Attribute attr) {
        String fieldInString = attr.getClass().getSimpleName();
        return new AttributePanel(
                fieldInString,
                attr.toString(),
                createEditHandler(getPersonPersonalField(fieldInString))
        );
    }

    /** Gets the PersonField enum type of attribute from label */
    private PersonAdapter.PersonField getPersonPersonalField(String fieldInString) {
        switch(fieldInString) {
        case "Name":
            return PersonAdapter.PersonField.NAME;
        case "Address":
            return PersonAdapter.PersonField.ADDRESS;
        case "Phone":
            return PersonAdapter.PersonField.PHONE;
        case "Email":
            return PersonAdapter.PersonField.EMAIL;
        default:
            throw new IllegalArgumentException("Unexpected Person Field used");
        }
    }

    private AttributePanel.EditHandler createEditHandler(PersonAdapter.PersonField field) {
        return newValue -> {
            try {
                this.personAdapter.edit(field, newValue);
                return null;
            } catch (InvalidFieldException e) {
                return e.getMessage();
            }
        };
    }

    /**
     * Updates the VBox content to the Client's personal Details
     */
    public void changeTabToPersonal() {
        refresh();
        personAdapter.getAllAttributesList().stream()
                .map(attr -> createAttributePanel(attr).getRoot())
                .forEach(y -> attributeDisplayContainer.getChildren().add(y));
    }

    /**
     * Updates the VBox content to the Client's policy Details
     */
    public void changeTabToPolicy() {
        refresh();
        attributeDisplayContainer.getChildren().add(
                new AttributeTable<Policy>(
                        policyTableConfig, personAdapter.getSubject().getPolicies()
                ).getRoot()
        );
    }

    /**
     * Updates the VBox content to the Client's Asset Details
     */
    public void changeTabToAssets() {
        refresh();
        attributeDisplayContainer.getChildren().add(
                new AttributeTable<Asset>(
                        assetTableConfig, personAdapter.getSubject().getAssets()
                ).getRoot()
        );
    }

    /**
     * Updates the VBox content to the Client's Liabilities Details
     */
    public void changeTabToLiabilities() {
        refresh();
        attributeDisplayContainer.getChildren().add(
                new AttributeTable<Liability>(
                        liabilityTableConfig, personAdapter.getSubject().getLiabilities()
                ).getRoot()
        );
    }

    /**
     * Updates the VBox content to the Client's Notes Details
     */
    public void changeTabToNotes() {
        refresh();
    }

    private void refresh() {
        attributeDisplayContainer.getChildren().clear();
    }

}
