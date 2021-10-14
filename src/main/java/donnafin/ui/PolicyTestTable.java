package donnafin.ui;

import java.util.List;

public class PolicyTestTable {

    static AttributeTable.TableConfig<PolicyTest> tableConfig = new AttributeTable.TableConfig<>(
            "Policy Test",
            List.of(
                new AttributeTable.ColumnConfig("Policy Name", "name", 400),
                new AttributeTable.ColumnConfig("Commission Value", "amt", 100)
            ),
            policyTestCollection -> String.format(
                    "Total Commission %.2f",
                    policyTestCollection.stream()
                            .map(PolicyTest::getAmt)
                            .map(Double::parseDouble)
                            .reduce(0.0, Double::sum)
            )
    );
    static List<PolicyTest> example = List.of(
            new PolicyTest("1.0", "uno"),
            new PolicyTest("2.4", "dos"),
            new PolicyTest("3.9", "tres")
    );
}
