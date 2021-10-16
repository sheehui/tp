package donnafin.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import donnafin.logic.PersonAdapter;
import donnafin.testutil.PersonBuilder;
import guitests.guihandles.ClientInfoPanelHandle;

public class ClientInfoPanelTest extends GuiUnitTest {

    private ClientInfoPanel clientInfoPanel;
    private ClientInfoPanelHandle clientInfoPanelHandle;

    @BeforeEach
    public void setUp() {
        clientInfoPanel = new ClientInfoPanel(new PersonAdapter(null, new PersonBuilder().build()));
        uiPartExtension.setUiPart(clientInfoPanel);

        clientInfoPanelHandle = new ClientInfoPanelHandle(
                getChildNode(clientInfoPanel.getRoot(),
                ClientInfoPanelHandle.CLIENT_INFO_PANEL_ID)
        );
    }

    @Test
    public void display() {
        // confirm that it can display.
    }
}
