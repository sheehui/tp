package donnafin.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guitests.guihandles.AttributeTableHandle;

public class AttributeTableTest extends GuiUnitTest {

    private AttributeTable<?> attributeTable;
    private AttributeTableHandle<?> attributeTableHandle;

    @BeforeEach
    public void setUp() {
        attributeTable = new AttributeTable<>(PolicyTestTable.getTableConfig(), PolicyTestTable.getExampleList());
        uiPartExtension.setUiPart(attributeTable);

        attributeTableHandle = new AttributeTableHandle<>(getChildNode(attributeTable.getRoot(),
                AttributeTableHandle.TABLE_DISPLAY_ID));
    }

    /** Do nothing but test that it can load properly */
    @Test
    public void display() {}
}
