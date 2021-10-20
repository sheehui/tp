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
        PERSONALINFORMATIONTAB,
        FINANCIALINFORMATIONTAB,
        LIABILITIESTAB,
        POLICIESTAB,
        NOTESTAB,
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

    public void setStatePersonalInformationTab() {
        logger.info("Set Ui state to personal information");
        this.state = ViewFinderState.PERSONALINFORMATIONTAB;
    }

    public void setStateFinancialInformationTab() {
        logger.info("Set Ui state to financial information");
        this.state = ViewFinderState.FINANCIALINFORMATIONTAB;
    }
    public void setStateLiabilitiesTab() {
        logger.info("Set Ui state to liabilities");
        this.state = ViewFinderState.LIABILITIESTAB;
    }

    public void setStatePoliciesTab() {
        logger.info("Set Ui state to policies");
        this.state = ViewFinderState.POLICIESTAB;
    }

    public void setStateNotesTab() {
        logger.info("Set Ui state to notes");
        this.state = ViewFinderState.NOTESTAB;
    }

    public ViewFinderState getState() {
        return state;
    }
}
