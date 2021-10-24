package donnafin.logic.commands;

import static donnafin.logic.parser.CliSyntax.PREFIX_COMMISSION;
import static donnafin.logic.parser.CliSyntax.PREFIX_INSURER;
import static donnafin.logic.parser.CliSyntax.PREFIX_INSURED_VALUE;
import static donnafin.logic.parser.CliSyntax.PREFIX_NAME;
import static donnafin.logic.parser.CliSyntax.PREFIX_REMARKS;
import static donnafin.logic.parser.CliSyntax.PREFIX_TYPE;
import static donnafin.logic.parser.CliSyntax.PREFIX_VALUE;
import static donnafin.logic.parser.CliSyntax.PREFIX_YEARLY_PREMIUM;

import static java.util.Objects.requireNonNull;

import donnafin.logic.commands.exceptions.CommandException;
import donnafin.logic.parser.ParserUtil;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.model.Model;
import donnafin.model.person.Asset;
import donnafin.model.person.Liability;
import donnafin.model.person.Person;
import donnafin.model.person.Policy;
import donnafin.ui.state.UiState;

public class AppendCommand extends Command {

    public static final String COMMAND_WORD = "append";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "notes down a policy/asset/liability owned by a client. "
        + "Parameters (For Policies): "
        + PREFIX_NAME + "POLICY NAME "
        + PREFIX_INSURER + "INSURER "
        + PREFIX_INSURED_VALUE + "INSURED VALUE "
        + PREFIX_YEARLY_PREMIUM + "YEARLY PREMIUM "
        + PREFIX_COMMISSION + "COMMISSION \n"
        + "Parameters (For Assets/Liabilities): "
        + PREFIX_NAME + "ASSET/LIABILITY NAME "
        + PREFIX_TYPE + "TYPE "
        + PREFIX_VALUE + "VALUE "
        + PREFIX_REMARKS + "REMARKS ";

    public static final String MESSAGE_SUCCESS = "New policy/asset/liability added: %1$s";

    private final String details;

    public AppendCommand(String details) {
        this.details = details;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        String tab = "placeholder";

        switch (tab) {
        case Policy:
            //use person adapter to append added policy to current policies
            break;
        case Asset:
            //use person adapter to append added policy to current assets
            break;
        case Liability:
            //use person adapter to append added policy to current liabilities
            break;
        default:
            throw new CommandException("This command cannot be used in this tab");
        }


        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppendCommand // instanceof handles nulls
                && client.equals(((AppendCommand) other).client, true));
    }
}
