package donnafin.ui.state;

import java.util.logging.Logger;

import donnafin.commons.core.LogsCenter;
import donnafin.ui.UiManager;

/**
Class to track the current state of the Ui. E.g which tab the ui is currently on
 Currently the class does not do anything as of yet
 */
public class UiState {

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private enum ViewFinderState {
        PERSONLISTPANEL,
        CLIENTINFOPANEL
    }

    private ViewFinderState state;

    /**
     * Constructor for Uistate
     */
    public UiState() {
        logger.info("Creating Ui State: Set to Home");
        this.state = ViewFinderState.PERSONLISTPANEL;
    }

    public void setStatePersonListPanel() {
        logger.info("Set Ui state to home");
        this.state = ViewFinderState.PERSONLISTPANEL;
    }

    public void setStateClientInfoPanel() {
        logger.info("Set Ui state to client");
        this.state = ViewFinderState.CLIENTINFOPANEL;
    }

    public ViewFinderState getState() {
        return state;
    }
}
