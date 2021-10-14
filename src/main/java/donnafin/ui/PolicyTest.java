package donnafin.ui;

import donnafin.model.person.Attribute;

import java.util.List;

public class PolicyTest implements Attribute {
    final String amt;
    final String name;

    PolicyTest(String amt, String name) {
        this.amt = amt;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getAmt() {
        return amt;
    }
}
