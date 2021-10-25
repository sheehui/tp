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

import donnafin.logic.PersonAdapter;
import donnafin.logic.commands.exceptions.CommandException;
import donnafin.logic.parser.ParserUtil;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.model.Model;
import donnafin.model.person.Asset;
import donnafin.model.person.Liability;
import donnafin.model.person.Person;
import donnafin.model.person.Policy;
import donnafin.ui.state.UiState;

import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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

    public static final String MESSAGE_SUCCESS = "New policy/asset/liability added";

    private final Consumer<PersonAdapter> editor;
    private PersonAdapter personAdapter;

    public AppendCommand(PersonAdapter personAdapter, Policy policy) {
        this.personAdapter = personAdapter;
        this.editor = pa -> {
            Set<Policy> policies = pa.getSubject().getPolicies().stream().collect(Collectors.toSet());
            policies.add(policy);
            pa.editPolicies(policies);
        };
    }

    public AppendCommand(PersonAdapter personAdapter, Liability liability) {
        this.personAdapter = personAdapter;
        this.editor = pa -> {
            Set<Liability> liabilities = pa.getSubject().getLiabilities().stream().collect(Collectors.toSet());
            liabilities.add(liability);
            pa.editLiabilities(liabilities);
        };
    }

    public AppendCommand(PersonAdapter personAdapter, Asset asset) {
        this.personAdapter = personAdapter;
        this.editor = pa -> {
            Set<Asset> assets = pa.getSubject().getAssets().stream().collect(Collectors.toSet());
            assets.add(asset);
            pa.editAssets(assets);
        };
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        editor.accept(this.personAdapter);

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppendCommand); // instanceof handles nulls
//                && client.equals(((AppendCommand) other).client, true));
    }
}
