package donnafin.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import donnafin.commons.core.GuiSettings;
import donnafin.commons.core.LogsCenter;
import donnafin.logic.commands.Command;
import donnafin.logic.commands.CommandResult;
import donnafin.logic.commands.exceptions.CommandException;
import donnafin.logic.parser.AddressBookParser;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.model.Model;
import donnafin.model.ReadOnlyAddressBook;
import donnafin.model.person.Person;
import javafx.collections.ObservableList;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);
    private final Model model;
    private final AddressBookParser addressBookParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model) {
        this.model = model;
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = addressBookParser.parseCommand(commandText.toLowerCase());
        commandResult = command.execute(model);

        try {
            model.saveAddressBook();
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
