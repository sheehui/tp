package guitests.guihandles;

import javafx.scene.Node;

public class ClientInfoPanelHandle extends NodeHandle<Node> {

    public static final String CLIENT_INFO_PANEL_ID = "#container";
    // the above means that it will actually pick out a child component, the vbox containing attribute list
    // instead of the real ClientInfoPanel. Required for GUI Test to have a target.

    public ClientInfoPanelHandle(Node clientInfoPanelNode) {
        super(clientInfoPanelNode);
    }
}
