//package seedu.address.ui;
//
//import javafx.scene.Node;
//import javafx.scene.layout.Region;
//import javafx.scene.layout.StackPane;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.io.TempDir;
//import seedu.address.logic.Logic;
//import seedu.address.logic.LogicManager;
//import seedu.address.logic.commands.CommandResult;
//import seedu.address.logic.commands.exceptions.CommandException;
//import seedu.address.logic.parser.exceptions.ParseException;
//import seedu.address.model.*;
//import seedu.address.storage.*;
//
//import java.nio.file.Path;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class CommandIntegrationTest {
//
//    @TempDir
//    public Path temporaryFolder;
//
//    private UiManager uiManager;
//    private UiManager backUpUiManager;
//    private Logic logic;
//    private Model model;
//    private Storage storage;
//
//    @BeforeEach
//    public void setUp() {
//        JsonAddressBookStorage addressBookStorage =
//                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
//        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
//
//
//        storage = new StorageManager(addressBookStorage, userPrefsStorage);
//        model = new ModelManager(new AddressBook(), new UserPrefs(), storage);
//        logic = new LogicManager(model);
//        uiManager = new UiManager(logic);
//        backUpUiManager = new UiManager(logic);
//    }
//
//    @Test
//    public void testExecuteSuccessNoUIAction() throws CommandException, ParseException {
//        String listCommand = "list";
//        String addCommand = "add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01";
//        String deleteCommand = "delete 1";
//        String clearCommand = "clear";
//
//        List<String> commandList = Arrays.asList(listCommand, addCommand, deleteCommand, clearCommand);
//
//        for (String command: commandList) {
//            CommandResult logicCommandResult = null;
//            CommandResult uiManagerCommandResult = null;
//
//            if (command.equals(addCommand)) {
//                // We cant add the same person twice, so we delete the person first and add
//                // him again to see if the result is the same
//                logicCommandResult = logic.execute(command);
//                logic.execute(clearCommand);
//                uiManagerCommandResult = uiManager.execute(command);
//                logic.execute(clearCommand);
//            } else if (command.equals(deleteCommand)) {
//                // We need something to delete, so we add the same person and delete him twice
//                // to check if the execute delete command is the same
//                logic.execute(addCommand);
//                logicCommandResult = logic.execute(command);
//                logic.execute(addCommand);
//                uiManagerCommandResult = uiManager.execute(command);
//            } else {
//                logicCommandResult = logic.execute(command);
//                uiManagerCommandResult = uiManager.execute(command);
//            }
//
//
//            assertEquals(logicCommandResult, uiManagerCommandResult);
//            assertEquals(backUpUiManager, uiManager);
//            uiManagerCommandResult.getUiAction().accept(uiManager);
//            assertEquals(backUpUiManager, uiManager);
//        }
//    }
//
//    @Test
//    public void testExecuteSuccessUiAction() throws CommandException, ParseException {
//        String viewCommand = "view 1";
//
//        CommandResult uiManagerCommandResult = uiManager.execute(viewCommand);
//
//        //Change the display of UI
//        uiManagerCommandResult.getUiAction().accept(uiManager);
//
//        //should get root of clientView
//        Node regionUi = uiManager.getMainWindow().getViewFinderPlaceholder().getChildren().get(0);
//
//
//        //Please advise how to do this
//        //Was thinking we have a state for the view finder and we just check that thoughts?
//    }
//
//    private void checkUiResultSame(UiManager firstUiManager, UiManager secondUiManager) {
//        assertEquals(firstUiManager, secondUiManager);
//    }
//}
