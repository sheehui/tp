package donnafin.logic.commands;

import static donnafin.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static donnafin.logic.parser.CliSyntax.PREFIX_EMAIL;
import static donnafin.logic.parser.CliSyntax.PREFIX_NAME;
import static donnafin.logic.parser.CliSyntax.PREFIX_PHONE;
import static donnafin.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.requireNonNull;

import java.util.function.Consumer;

import donnafin.logic.InvalidFieldException;
import donnafin.logic.PersonAdapter;
import donnafin.logic.commands.exceptions.CommandException;
import donnafin.model.Model;
import donnafin.model.person.Address;
import donnafin.model.person.Email;
import donnafin.model.person.Name;
import donnafin.model.person.Notes;
import donnafin.model.person.Phone;


/**
 * Edits the details of an existing person in the address book.
 */
public class    EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final PersonAdapter personAdapter;
    private final Consumer<PersonAdapter> consumerPA;


    /**
     * @param personAdapter of the person in the filtered person list to edit
     * @param name new name for the contact.
     */
    public EditCommand(PersonAdapter personAdapter, Name name) {
        requireNonNull(personAdapter);
        requireNonNull(name);

        this.personAdapter = personAdapter;
        this.consumerPA = x -> {
            personAdapter.edit(name);
        };
    }

    /**
     * @param personAdapter of the person in the filtered person list to edit
     * @param phone new phone number for the contact.
     */
    public EditCommand(PersonAdapter personAdapter, Phone phone) {
        requireNonNull(personAdapter);
        requireNonNull(phone);

        this.personAdapter = personAdapter;
        this.consumerPA = x -> {
            personAdapter.edit(phone);
        };
    }

    /**
     * @param personAdapter of the person in the filtered person list to edit
     * @param email new email for the contact.
     */
    public EditCommand(PersonAdapter personAdapter, Email email) {
        requireNonNull(personAdapter);
        requireNonNull(email);

        this.personAdapter = personAdapter;
        this.consumerPA = x -> {
            personAdapter.edit(email);
        };
    }

    /**
     * @param personAdapter of the person in the filtered person list to edit
     * @param address new address for the contact.
     */
    public EditCommand(PersonAdapter personAdapter, Address address) {
        requireNonNull(personAdapter);
        requireNonNull(address);

        this.personAdapter = personAdapter;
        this.consumerPA = x -> {
            personAdapter.edit(address);
        };
    }

    /**
     * @param personAdapter of the person in the filtered person list to edit
     * @param notes new notes for the contact.
     */
    public EditCommand(PersonAdapter personAdapter, Notes notes) {
        requireNonNull(personAdapter);
        requireNonNull(notes);

        this.personAdapter = personAdapter;
        this.consumerPA = x -> {
            personAdapter.edit(notes);
        };
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        consumerPA.accept(personAdapter);

        return new CommandResult(MESSAGE_EDIT_PERSON_SUCCESS);
    }


}

