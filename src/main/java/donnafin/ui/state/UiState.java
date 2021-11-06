package donnafin.ui.state;

import java.util.logging.Logger;

import donnafin.commons.core.LogsCenter;
import donnafin.ui.Ui.ViewFinderState;
import donnafin.ui.UiManager;

/**
 * Class to track the current state of the Ui. E.g. which tab the ui is currently on
 */
public class UiState {

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private ViewFinderState state;


    /** Constructor for {@code UiState}. Initialises at PERSON_LIST_PANEL, aka "Home" */
    public UiState() {
        logger.info("Creating Ui State: Set to Home");
        this.state = ViewFinderState.PERSON_LIST_PANEL;
    }

    public void setStatePersonListPanel() {
        logger.info("Set Ui state to home");
        this.state = ViewFinderState.PERSON_LIST_PANEL;
    }

    public void setStatePersonalInformationTab() {
        logger.info("Set Ui state to personal information");
        this.state = ViewFinderState.CONTACT;
    }

    public void setStateFinancialInformationTab() {
        logger.info("Set Ui state to financial information");
        this.state = ViewFinderState.ASSETS;
    }
    public void setStateLiabilitiesTab() {
        logger.info("Set Ui state to liabilities");
        this.state = ViewFinderState.LIABILITIES;
    }

    public void setStatePoliciesTab() {
        logger.info("Set Ui state to policies");
        this.state = ViewFinderState.POLICIES;
    }

    public void setStateNotesTab() {
        logger.info("Set Ui state to notes");
        this.state = ViewFinderState.NOTES;
    }

    public ViewFinderState getState() {
        return state;
    }
}
