//@@author bharathcs-reused
//Reused from https://github.com/se-edu/addressbook-level4/ with minor modifications.
package donnafin.ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;

import guitests.guihandles.HelpWindowHandle;

public class HelpWindowTest extends GuiUnitTest {

    private HelpWindow helpWindow;
    private HelpWindowHandle helpWindowHandle;

    @BeforeEach
    public void setUp() throws Exception {
        guiRobot.interact(() -> helpWindow = new HelpWindow());
        FxToolkit.registerStage(helpWindow::getRoot);
        helpWindowHandle = new HelpWindowHandle(helpWindow.getRoot());
    }

    @Test
    public void display() throws Exception {
        FxToolkit.showStage();
    }

    @Test
    public void isShowing_helpWindowIsShowing_returnsTrue() {
        guiRobot.interact(helpWindow::show);
        assertTrue(helpWindow.isShowing());
    }

    @Test
    public void isShowing_helpWindowIsHiding_returnsFalse() {
        assertFalse(helpWindow.isShowing());
    }
}

//@@author
