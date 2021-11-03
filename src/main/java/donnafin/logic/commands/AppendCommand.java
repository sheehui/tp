package donnafin.logic.commands;

import static donnafin.logic.parser.CliSyntax.PREFIX_COMMISSION;
import static donnafin.logic.parser.CliSyntax.PREFIX_INSURED_VALUE;
import static donnafin.logic.parser.CliSyntax.PREFIX_INSURER;
import static donnafin.logic.parser.CliSyntax.PREFIX_NAME;
import static donnafin.logic.parser.CliSyntax.PREFIX_REMARKS;
import static donnafin.logic.parser.CliSyntax.PREFIX_TYPE;
import static donnafin.logic.parser.CliSyntax.PREFIX_VALUE;
import static donnafin.logic.parser.CliSyntax.PREFIX_YEARLY_PREMIUM;
import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

import donnafin.logic.PersonAdapter;
import donnafin.logic.commands.exceptions.CommandException;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.model.Model;
import donnafin.model.person.Asset;
import donnafin.model.person.Liability;
import donnafin.model.person.Policy;
import donnafin.ui.Ui;


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

    public static final String MESSAGE_SUCCESS_ASSET = "New asset added";
    public static final String MESSAGE_SUCCESS_LIABILITY = "New liability added";
    public static final String MESSAGE_SUCCESS_POLICY = "New policy added";


    public static final String MESSAGE_DUPLICATE_ASSET = "Sorry, the asset with this exact specifications"
            + " has already been added!";
    public static final String MESSAGE_DUPLICATE_LIABILITY = "Sorry, the liability with this exact specifications "
            + "has already been added!";
    public static final String MESSAGE_DUPLICATE_POLICY = "Sorry, the policy with this exact specifications"
            + " has already been added!";

    public static final String MESSAGE_SIMILAR_ASSET = "New asset added. "
            + "Warning! An asset with a similar name has been added previously.";
    public static final String MESSAGE_SIMILAR_LIABILITY = "New liability added. "
            + "Warning! A liability with a similar name has been added previously.";
    public static final String MESSAGE_SIMILAR_POLICY = "New policy added. "
            + "Warning! A policy with a similar name has been added previously.";

    private static String messageRESULT;
    private final Consumer<PersonAdapter> editor;
    private final PersonAdapter personAdapter;
    private final Object hashableNewValue;

    /**
     * The append command used to add a new policy associated with the client.
     * @param personAdapter the client being edited.
     * @param policy the new policy being added.
     */
    public AppendCommand(PersonAdapter personAdapter, Policy policy) {
        requireNonNull(personAdapter);
        requireNonNull(policy);
        this.personAdapter = personAdapter;
        this.hashableNewValue = policy;
        this.editor = pa -> {
            Set<Policy> policies = new HashSet<>(pa.getSubject().getPolicies());
            boolean containsSimilarName = policies.stream().anyMatch(item ->
                    item.getName().equalsIgnoreCase(policy.getName()));
            if (policies.contains(policy)) {
                try {
                    messageRESULT = MESSAGE_DUPLICATE_POLICY;
                    throw new CommandException(MESSAGE_DUPLICATE_POLICY);
                } catch (CommandException e) {
                    e.printStackTrace();
                }
            } else {
                policies.add(policy);
                pa.editPolicies(policies);
                messageRESULT = containsSimilarName ? MESSAGE_SIMILAR_POLICY : MESSAGE_SUCCESS_POLICY;
            }
        };
    }

    /**
     * The append command used to add a new liability associated with the client.
     * @param personAdapter the client being edited.
     * @param liability the new liability being added.
     */
    public AppendCommand(PersonAdapter personAdapter, Liability liability) {
        requireNonNull(personAdapter);
        requireNonNull(liability);
        this.personAdapter = personAdapter;
        this.hashableNewValue = liability;
        this.editor = pa -> {
            Set<Liability> liabilities = new HashSet<>(pa.getSubject().getLiabilities());
            boolean containsSimilarName = liabilities.stream().anyMatch(item ->
                    item.getName().equalsIgnoreCase(liability.getName()));
            if (liabilities.contains(liability)) {
                try {
                    messageRESULT = MESSAGE_DUPLICATE_LIABILITY;
                    throw new CommandException(MESSAGE_DUPLICATE_LIABILITY);
                } catch (CommandException e) {
                    e.printStackTrace();
                }
            } else {
                liabilities.add(liability);
                pa.editLiabilities(liabilities);
                messageRESULT = containsSimilarName ? MESSAGE_SIMILAR_LIABILITY : MESSAGE_SUCCESS_LIABILITY;

            }
        };
    }

    /**
     * The append command used to add a new asset associated with the client.
     * @param personAdapter the client being edited.
     * @param asset the new asset being added.
     */
    public AppendCommand(PersonAdapter personAdapter, Asset asset) {
        requireNonNull(personAdapter);
        requireNonNull(asset);
        this.personAdapter = personAdapter;
        this.hashableNewValue = asset;
        this.editor = pa -> {
            Set<Asset> assets = new HashSet<>(pa.getSubject().getAssets());
            boolean containsSimilarName = assets.stream().anyMatch(item ->
                    item.getName().equalsIgnoreCase(asset.getName()));
            if (assets.contains(asset)) {
                try {
                    messageRESULT = MESSAGE_DUPLICATE_ASSET;
                    throw new CommandException(MESSAGE_DUPLICATE_ASSET);
                } catch (CommandException e) {
                    e.printStackTrace();
                }
            } else {
                assets.add(asset);
                pa.editAssets(assets);
                messageRESULT = containsSimilarName ? MESSAGE_SIMILAR_ASSET : MESSAGE_SUCCESS_ASSET;
            }
        };
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        editor.accept(this.personAdapter);

        //Used to refresh UI to display new attribute added
        Consumer<Ui> refresh = x -> {
            try {
                x.switchClientViewTab(x.getUiState());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        };

        return new CommandResult(messageRESULT, refresh);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof AppendCommand)) {
            return false;
        }
        return ((AppendCommand) o).personAdapter.equals(personAdapter)
                && ((AppendCommand) o).hashableNewValue.hashCode() == this.hashableNewValue.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(personAdapter, hashableNewValue);
    }
}
