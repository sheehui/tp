package donnafin.ui;

import donnafin.model.person.Attribute;

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

    @Override
    public boolean isPossibleDuplicate(Attribute other) {
        return false;
    }
}
