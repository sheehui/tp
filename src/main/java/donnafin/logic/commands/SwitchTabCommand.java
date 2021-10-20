package donnafin.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Consumer;

import donnafin.logic.Logic;
import donnafin.logic.commands.exceptions.CommandException;
import donnafin.logic.parser.ClientViewParser;
import donnafin.model.Model;
import donnafin.ui.Ui;
import donnafin.ui.Ui.ClientViewTab;

public class SwitchTabCommand extends Command {

    public static final String COMMAND_WORD = "tab";
    private static final String MESSAGE_SUCCESS = "Switched tab";

    private final ClientViewTab tab;

    public SwitchTabCommand(ClientViewTab tab) {
        this.tab = tab;
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
            logic.setParserStrategy(new ClientViewParser());
            // TODO: when we have exactly which parser (WHICH TAB)
        };
        return new CommandResult(MESSAGE_SUCCESS, uiAction, logicAction);
    }
}
