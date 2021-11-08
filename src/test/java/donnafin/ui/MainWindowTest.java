package donnafin.ui;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.testfx.api.FxToolkit;

import donnafin.logic.Logic;
import donnafin.logic.LogicManager;
import donnafin.model.AddressBook;
import donnafin.model.Model;
import donnafin.model.ModelManager;
import donnafin.model.UserPrefs;
import donnafin.storage.JsonAddressBookStorage;
import donnafin.storage.JsonUserPrefsStorage;
import donnafin.storage.StorageManager;
import guitests.guihandles.MainWindowHandle;
import javafx.stage.Stage;

public class MainWindowTest {
    @TempDir
    public Path temporaryFolder;

    private MainWindow mainWindow;
    private MainWindowHandle mainWindowHandle;
    private Stage stage;

    @BeforeEach
    public void setUp() throws Exception {
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        Model model = new ModelManager(new AddressBook(), new UserPrefs(), storage);
        Logic logic = new LogicManager(model);
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupStage(stage -> {
            this.stage = stage;
            mainWindow = new MainWindow(stage, new UiManager(logic));
            mainWindow.fillInnerParts();
            mainWindowHandle = new MainWindowHandle(stage);
        });
        FxToolkit.showStage();
    }

    /** Do nothing but test that it can load properly */
    @Test
    public void display() {}
}
