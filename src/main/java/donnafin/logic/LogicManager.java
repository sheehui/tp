package donnafin.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.logging.Logger;

import donnafin.commons.core.GuiSettings;
import donnafin.commons.core.LogsCenter;
import donnafin.logic.commands.Command;
import donnafin.logic.commands.CommandResult;
import donnafin.logic.commands.exceptions.CommandException;
import donnafin.logic.parser.AddressBookParser;
import donnafin.logic.parser.ParserContext;
import donnafin.logic.parser.ParserStrategy;
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
    private final ParserContext parserContext;
    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model) {
        this.model = model;
        parserContext = new ParserContext(new AddressBookParser());
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = parserContext.executeParserStrategyCommand(commandText);
        commandResult = command.execute(model);

        //Implement any changes logic that needs to happen
        Consumer<Logic> logicAction = commandResult.getLogicAction();
        assert !logicAction.equals(null) : "commandResult.uiAction was set as null";
        if (!logicAction.equals(null)) {
            logicAction.accept(this);
        }

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

    /**
    Function to change the strategyParser in parserContext
     */
    public void setParserStrategy(ParserStrategy strategyParser) {
        parserContext.setCurrentParserStrategy(strategyParser);
    }
}
