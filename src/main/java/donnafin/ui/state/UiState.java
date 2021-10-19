package donnafin.ui.state;

import donnafin.commons.core.LogsCenter;
import donnafin.ui.UiManager;

import java.util.logging.Logger;

/*
Class to track the current state of the Ui. E.g which tab the ui is currently on
 */
public class UiState {

    private enum viewFinderState {
        PERSONLISTPANEL,
        CLIENTINFOPANEL
    }

    private viewFinderState state;
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);

    public UiState() {
        logger.info("Creating Ui State: Set to Home");
        this.state = viewFinderState.PERSONLISTPANEL;
    }

    public void setStatePersonListPanel() {
        logger.info("Set Ui state to home");
        this.state = viewFinderState.PERSONLISTPANEL;
    }

    public void setStateClientInfoPanel() {
        logger.info("Set Ui state to client");
        this.state = viewFinderState.CLIENTINFOPANEL;
    }

    public viewFinderState getState() {
        return state;
    }
}
