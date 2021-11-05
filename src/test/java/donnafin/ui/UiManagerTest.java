//@@author bharathcs-reused
//Reused from https://github.com/se-edu/addressbook-level4/ with minor modifications.
package donnafin.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import donnafin.commons.core.GuiSettings;
import donnafin.logic.Logic;
import donnafin.logic.LogicManager;
import donnafin.logic.commands.CommandResult;
import donnafin.logic.commands.exceptions.CommandException;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.model.AddressBook;
import donnafin.model.Model;
import donnafin.model.ModelManager;
import donnafin.model.UserPrefs;
import donnafin.model.person.Person;
import donnafin.storage.JsonAddressBookStorage;
import donnafin.storage.JsonUserPrefsStorage;
import donnafin.storage.StorageManager;
import donnafin.testutil.Assert;
import javafx.collections.ObservableList;

public class UiManagerTest {

    @TempDir
    public Path temporaryFolder;

    private UiManager uiManager;
    private UiManager backUpUiManager;
    private Logic logic;
    private Model model;

    @BeforeEach
    public void setUp() {
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        this.model = new ModelManager(new AddressBook(), new UserPrefs(), storage);
        logic = new LogicManager(model);
        uiManager = new UiManager(logic);
        backUpUiManager = new UiManager(logic);
    }

    @Test
    public void testGetGuiSettings() {
        GuiSettings logicGuiSettings = logic.getGuiSettings();
        GuiSettings uiManagerGuiSettings = uiManager.getGuiSettings();

        assertEquals(logicGuiSettings, uiManagerGuiSettings);
    }

    @Test
    public void testExecuteSuccessNoUiAction() throws CommandException, ParseException {
        String listCommand = "list";
        String addCommand = "add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01"
                + " l/INSERT LIABILITY HERE c/INSERT COMMISSION HERE";
        String deleteCommand = "delete 1";
        String clearCommand = "clear";

        List<String> commandList = Arrays.asList(listCommand, addCommand, deleteCommand, clearCommand);

        for (String command: commandList) {
            CommandResult logicCommandResult;
            CommandResult uiManagerCommandResult;

            if (command.equals(addCommand)) {
                // We cant add the same person twice, so we delete the person first and add
                // him again to see if the result is the same
                logicCommandResult = logic.execute(command);
                logic.execute(clearCommand);
                uiManagerCommandResult = uiManager.execute(command);
                logic.execute(clearCommand);
            } else if (command.equals(deleteCommand)) {
                // We need something to delete, so we add the same person and delete him twice
                // to check if the execute delete command is the same
                logic.execute(addCommand);
                logicCommandResult = logic.execute(command);
                logic.execute(addCommand);
                uiManagerCommandResult = uiManager.execute(command);
            } else {
                logicCommandResult = logic.execute(command);
                uiManagerCommandResult = uiManager.execute(command);
            }


            assertEquals(logicCommandResult, uiManagerCommandResult);
            assertEquals(backUpUiManager, uiManager);
            uiManagerCommandResult.getUiAction().accept(uiManager);
            assertEquals(backUpUiManager, uiManager);
        }
    }

    @Test
    public void testGetFilteredPersonList() {
        ObservableList<Person> logicFilteredPersonList = logic.getFilteredPersonList();
        ObservableList<Person> uiManagerFilteredPersonList = uiManager.getFilteredPersonList();


        assertEquals(logicFilteredPersonList, uiManagerFilteredPersonList);
    }

    @Test
    public void testGetAddressBookFilePath() {
        Path logicFilePath = logic.getAddressBookFilePath();
        Path uiManagerFilePath = uiManager.getAddressBookFilePath();

        assertEquals(logicFilePath, uiManagerFilePath);
    }

    /*
    Unclean way of testing for parse exception
     */
    @Test
    public void testExecuteFailThrowsParseException() {
        String invalidCommand = "uicfhmowqewca";
        Throwable logicParseException = null;
        try {
            logic.execute(invalidCommand);
        } catch (ParseException pe) {
            logicParseException = pe;
        } catch (Exception e) {
            e.printStackTrace();
        }

        Throwable uiManagerParseException = null;
        try {
            uiManager.execute(invalidCommand);
        } catch (ParseException pe) {
            uiManagerParseException = pe;
        } catch (Exception e) {
            e.printStackTrace();
        }

        // the exceptions are not equal. Do not want to override the method so
        // I simply check if the messages are the same
        assertEquals(logicParseException.getMessage(), uiManagerParseException.getMessage());
    }

    @Test void testExecuteFailThrowsCommandException() {
        String invalidCommand = "delete 9";
        Throwable logicCommandException = null;
        try {
            logic.execute(invalidCommand);
        } catch (CommandException ce) {
            logicCommandException = ce;
        } catch (Exception e) {
            e.printStackTrace();
        }

        Throwable uiManagerCommandException = null;
        try {
            uiManager.execute(invalidCommand);
        } catch (CommandException ce) {
            uiManagerCommandException = ce;
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(logicCommandException.getMessage(), uiManagerCommandException.getMessage());

    }

    @Test
    public void testSetGuiSettings() {
        // Should be the same if i call from logic or UiManager since I tested this previously already
        GuiSettings currentGuiSettings = logic.getGuiSettings();
        GuiSettings newGuiSettings = new GuiSettings();

        GuiSettings logicGuiSettingsAfterSet;
        GuiSettings uiManagerGuiSettingsAfterSet;

        logic.setGuiSettings(newGuiSettings);
        logicGuiSettingsAfterSet = logic.getGuiSettings();

        uiManager.setGuiSettings(newGuiSettings);
        uiManagerGuiSettingsAfterSet = logic.getGuiSettings();

        assertEquals(logicGuiSettingsAfterSet, uiManagerGuiSettingsAfterSet);
    }

    @Test
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage) {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), null);
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }


    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage, Model expectedModel) {
        Assert.assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }


    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

}

//@@author
