package donnafin.logic.commands;

import static java.util.Objects.requireNonNull;
import static donnafin.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static donnafin.logic.parser.CliSyntax.PREFIX_EMAIL;
import static donnafin.logic.parser.CliSyntax.PREFIX_NAME;
import static donnafin.logic.parser.CliSyntax.PREFIX_PHONE;
import static donnafin.logic.parser.CliSyntax.PREFIX_TAG;
import static donnafin.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import donnafin.commons.core.types.Index;
import donnafin.logic.InvalidFieldException;
import donnafin.logic.PersonAdapter;
import donnafin.logic.commands.Command;
import donnafin.logic.commands.CommandResult;
import donnafin.commons.core.Messages;
import donnafin.commons.util.CollectionUtil;
import donnafin.logic.commands.exceptions.CommandException;
import donnafin.model.Model;
import donnafin.model.person.Address;
import donnafin.model.person.Asset;
import donnafin.model.person.Email;
import donnafin.model.person.Liability;
import donnafin.model.person.Name;
import donnafin.model.person.Notes;
import donnafin.model.person.Person;
import donnafin.model.person.Phone;
import donnafin.model.person.Policy;
import donnafin.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

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
     * @param liability new liability to be edited.
     */
    public EditCommand(PersonAdapter personAdapter, Liability liability) {
        requireNonNull(personAdapter);
        requireNonNull(liability);

        this.personAdapter = personAdapter;
        PersonAdapter.PersonField personField = PersonAdapter.PersonField.LIABILITIES;
        this.consumerPA = x -> {
            try {
                personAdapter.edit(personField, liability.toString());
            } catch (InvalidFieldException e){
                e.printStackTrace();
            }
        };
    }

    public EditCommand(PersonAdapter personAdapter, Policy policy) {
        requireNonNull(personAdapter);
        requireNonNull(policy);

        this.personAdapter = personAdapter;
        PersonAdapter.PersonField personField = PersonAdapter.PersonField.POLICIES;
        this.consumerPA = x -> {
            try {
                personAdapter.edit(personField, policy.toString());
            } catch (InvalidFieldException e){
                e.printStackTrace();
            }
        };
    }

    public EditCommand(PersonAdapter personAdapter, Asset asset) {
        requireNonNull(personAdapter);
        requireNonNull(asset);

        this.personAdapter = personAdapter;
        PersonAdapter.PersonField personField = PersonAdapter.PersonField.ASSETS;
        this.consumerPA = x -> {
            try {
                personAdapter.edit(personField, asset.toString());
            } catch (InvalidFieldException e){
                e.printStackTrace();
            }
        };
    }

    public EditCommand(PersonAdapter personAdapter, Name name) {
        requireNonNull(personAdapter);
        requireNonNull(name);

        this.personAdapter = personAdapter;
        PersonAdapter.PersonField personField = PersonAdapter.PersonField.NAME;
        this.consumerPA = x -> {
            try {
                personAdapter.edit(personField, name.toString());
            } catch (InvalidFieldException e){
                e.printStackTrace();
            }
        };
    }

    public EditCommand(PersonAdapter personAdapter, Phone phone) {
        requireNonNull(personAdapter);
        requireNonNull(phone);

        this.personAdapter = personAdapter;
        PersonAdapter.PersonField personField = PersonAdapter.PersonField.PHONE;
        this.consumerPA = x -> {
            try {
                personAdapter.edit(personField, phone.toString());
            } catch (InvalidFieldException e){
                e.printStackTrace();
            }
        };
    }

    public EditCommand(PersonAdapter personAdapter, Email email) {
        requireNonNull(personAdapter);
        requireNonNull(email);

        this.personAdapter = personAdapter;
        PersonAdapter.PersonField personField = PersonAdapter.PersonField.EMAIL;
        this.consumerPA = x -> {
            try {
                personAdapter.edit(personField, email.toString());
            } catch (InvalidFieldException e){
                e.printStackTrace();
            }
        };
    }

    public EditCommand(PersonAdapter personAdapter, Address address) {
        requireNonNull(personAdapter);
        requireNonNull(address);

        this.personAdapter = personAdapter;
        PersonAdapter.PersonField personField = PersonAdapter.PersonField.ADDRESS;
        this.consumerPA = x -> {
            try {
                personAdapter.edit(personField, address.toString());
            } catch (InvalidFieldException e){
                e.printStackTrace();
            }
        };
    }

    public EditCommand(PersonAdapter personAdapter, Tag tags) {
        requireNonNull(personAdapter);
        requireNonNull(tags);

        this.personAdapter = personAdapter;
        PersonAdapter.PersonField personField = PersonAdapter.PersonField.TAGS;
        this.consumerPA = x -> {
            try {
                personAdapter.edit(personField, tags.toString());
            } catch (InvalidFieldException e){
                e.printStackTrace();
            }
        };
    }

    public EditCommand(PersonAdapter personAdapter, Notes notes) {
        requireNonNull(personAdapter);
        requireNonNull(notes);

        this.personAdapter = personAdapter;
        PersonAdapter.PersonField personField = PersonAdapter.PersonField.NOTES;
        this.consumerPA = x -> {
            try {
                personAdapter.edit(personField, notes.toString());
            } catch (InvalidFieldException e){
                e.printStackTrace();
            }
        };
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        consumerPA.accept(personAdapter);

        return new CommandResult(MESSAGE_EDIT_PERSON_SUCCESS);
    }


}

