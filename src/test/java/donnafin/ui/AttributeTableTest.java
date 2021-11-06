package donnafin.ui;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import donnafin.model.person.Attribute;
import guitests.guihandles.AttributeTableHandle;

public class AttributeTableTest extends GuiUnitTest {

    private static class AttributeTableStub {
        private static class AttributeStub implements Attribute {
            final String amt;
            final String name;

            AttributeStub(String amt, String name) {
                this.amt = amt;
                this.name = name;
            }

            public String getName() {
                return name;
            }

            public String getAmt() {
                return amt;
            }

            @Override
            public boolean isPossibleDuplicate(Attribute other) {
                return false;
            }
        }

        private static final AttributeTable.AggregatorLabelCreator<AttributeStub> summariseCollectionOfPolicyTests =
            policyTestCollection -> String.format(
                    "Total Value %.2f",
                    policyTestCollection.stream()
                            .map(AttributeStub::getAmt)
                            .map(Double::parseDouble)
                            .reduce(0.0, Double::sum)
            );
        private static final List<AttributeTable.ColumnConfig> columnConfigs = List.of(
                new AttributeTable.ColumnConfig("Policy Name", "name", 400, 400),
                new AttributeTable.ColumnConfig("Value", "amt", 100, 100)
        );

        private static final AttributeTable.TableConfig<AttributeStub> tableConfig = new AttributeTable.TableConfig<>(
                "Policy Test", columnConfigs, summariseCollectionOfPolicyTests);
        private static final List<AttributeStub> exampleList = List.of(
                new AttributeStub("1.0", "uno"),
                new AttributeStub("2.4", "dos"),
                new AttributeStub("3.9", "tres")
        );
    }

    private AttributeTable<?> attributeTable;
    private AttributeTableHandle<?> attributeTableHandle;

    @BeforeEach
    public void setUp() {
        attributeTable = new AttributeTable<>(AttributeTableStub.tableConfig, AttributeTableStub.exampleList);
        uiPartExtension.setUiPart(attributeTable);

        attributeTableHandle = new AttributeTableHandle<>(getChildNode(attributeTable.getRoot(),
                AttributeTableHandle.TABLE_DISPLAY_ID));
    }

    /** Do nothing but test that it can load properly */
    @Test
    public void display() {}
}
