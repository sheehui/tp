//@@author bharathcs-reused
//Reused from https://github.com/se-edu/addressbook-level4/ with minor modifications.
package guitests.guihandles;

import javafx.scene.Node;

public class ClientInfoPanelHandle extends NodeHandle<Node> {

    public static final String CLIENT_INFO_PANEL_ID = "#root";
    public static final String CONTACT_BUTTON_ID = "#contact";
    public static final String POLICIES_BUTTON_ID = "#policies";
    public static final String ASSETS_BUTTON_ID = "#assets";
    public static final String LIABILITIES_BUTTON_ID = "#liabilities";
    public static final String NOTES_BUTTON_ID = "#notes";
    // the above means that it will actually pick out a child component, the vbox containing attribute list
    // instead of the real ClientInfoPanel. Required for GUI Test to have a target.

    public ClientInfoPanelHandle(Node clientInfoPanelNode) {
        super(clientInfoPanelNode);
    }
}
