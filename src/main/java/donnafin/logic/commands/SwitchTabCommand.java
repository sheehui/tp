package donnafin.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Consumer;

import donnafin.logic.Logic;
import donnafin.logic.PersonAdapter;
import donnafin.logic.commands.exceptions.CommandException;
import donnafin.logic.parser.AssetsTabParser;
import donnafin.logic.parser.ContactTabParser;
import donnafin.logic.parser.LiabilitiesTabParser;
import donnafin.logic.parser.NotesTabParser;
import donnafin.logic.parser.PolicyTabParser;
import donnafin.model.Model;
import donnafin.ui.Ui;

public class SwitchTabCommand extends Command {

    public static final String COMMAND_WORD = "tab";
    private static final String MESSAGE_SUCCESS = "Switched tab";

    private final Ui.ViewFinderState tab;
    private final PersonAdapter personAdapter;

    /** Constructor for switchTabCommand */
    public SwitchTabCommand(Ui.ViewFinderState tab, PersonAdapter personAdapter) {
        this.tab = tab;
        this.personAdapter = personAdapter;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Consumer<Ui> uiAction = ui -> ui.switchClientViewTab(tab);

        Consumer<Logic> logicAction = logic -> {
            switch (tab) {
            case CONTACT:
                logic.setParserStrategy(new ContactTabParser(personAdapter));
                break;
            case POLICIES:
                logic.setParserStrategy(new PolicyTabParser(personAdapter));
                break;
            case LIABILITIES:
                logic.setParserStrategy(new LiabilitiesTabParser(personAdapter));
                break;
            case ASSETS:
                logic.setParserStrategy(new AssetsTabParser(personAdapter));
                break;
            case NOTES:
                logic.setParserStrategy(new NotesTabParser(personAdapter));
                break;
            default:
                // Should throw an error and should never reach here
                throw new RuntimeException("Invalid enum for ViewFinderState");
            }
        };
        return new CommandResult(MESSAGE_SUCCESS, uiAction, logicAction);
    }
}
