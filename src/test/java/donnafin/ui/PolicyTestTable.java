package donnafin.ui;

import java.util.List;

public class PolicyTestTable {

    private static final AttributeTable.AggregatorLabelCreator<PolicyTest> summariseCollectionOfPolicyTests =
        policyTestCollection -> String.format(
            "Total Value %.2f",
                policyTestCollection.stream()
                    .map(PolicyTest::getAmt)
                    .map(Double::parseDouble)
                    .reduce(0.0, Double::sum)
        );
    private static final List<AttributeTable.ColumnConfig> columnConfigs = List.of(
            new AttributeTable.ColumnConfig("Policy Name", "name", 400, 400),
            new AttributeTable.ColumnConfig("Value", "amt", 100, 100)
    );

    private static final AttributeTable.TableConfig<PolicyTest> tableConfig = new AttributeTable.TableConfig<>(
            "Policy Test", columnConfigs, summariseCollectionOfPolicyTests);
    private static final List<PolicyTest> exampleList = List.of(
            new PolicyTest("1.0", "uno"),
            new PolicyTest("2.4", "dos"),
            new PolicyTest("3.9", "tres")
    );

    public static AttributeTable.TableConfig<PolicyTest> getTableConfig() {
        return tableConfig;
    }

    public static List<PolicyTest> getExampleList() {
        return exampleList;
    }
}
