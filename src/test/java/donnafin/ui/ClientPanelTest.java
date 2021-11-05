//@@author bharathcs-reused
//Reused from https://github.com/se-edu/addressbook-level4/ with minor modifications.
package donnafin.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import donnafin.logic.PersonAdapter;
import donnafin.testutil.PersonBuilder;
import guitests.guihandles.ClientInfoPanelHandle;

public class ClientPanelTest extends GuiUnitTest {

    private ClientPanel clientPanel;
    private ClientInfoPanelHandle clientInfoPanelHandle;

    @BeforeEach
    public void setUp() {
        clientPanel = new ClientPanel(new PersonAdapter(null, new PersonBuilder().build()), null);
        uiPartExtension.setUiPart(clientPanel);

        clientInfoPanelHandle = new ClientInfoPanelHandle(
                getChildNode(clientPanel.getRoot(),
                ClientInfoPanelHandle.CLIENT_INFO_PANEL_ID)
        );
    }

    @Test
    public void display() {
        // confirm that it can display.
    }
}

//@@author
