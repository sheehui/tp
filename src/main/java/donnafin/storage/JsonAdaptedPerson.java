package donnafin.storage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import donnafin.commons.core.LogsCenter;
import donnafin.commons.exceptions.IllegalValueException;
import donnafin.model.person.Address;
import donnafin.model.person.Asset;
import donnafin.model.person.Email;
import donnafin.model.person.Liability;
import donnafin.model.person.Name;
import donnafin.model.person.Notes;
import donnafin.model.person.Person;
import donnafin.model.person.Phone;
import donnafin.model.person.Policy;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Client's %s field is missing!";
    public static final String DUPLICATE_ATTRIBUTE_IN_LIST = "Client's %s field has duplicates!";
    private static final Logger logger = LogsCenter.getLogger(JsonAdaptedPerson.class);

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String notes;
    private final List<JsonAdaptedPolicy> policies;
    private final List<JsonAdaptedAsset> assets;
    private final List<JsonAdaptedLiability> liabilities;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(
            @JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("notes") String notes,
            @JsonProperty("policies") List<JsonAdaptedPolicy> policies,
            @JsonProperty("liabilities") List<JsonAdaptedLiability> liabilities,
            @JsonProperty("assets") List<JsonAdaptedAsset> assets) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.notes = notes;
        this.policies = policies;
        this.liabilities = liabilities;
        this.assets = assets;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        notes = source.getNotes().notes;
        policies = source.getPolicies().stream()
                .map(JsonAdaptedPolicy::new)
                .collect(Collectors.toList());
        assets = source.getAssets().stream()
                .map(JsonAdaptedAsset::new)
                .collect(Collectors.toList());
        liabilities = source.getLiabilities().stream()
                .map(JsonAdaptedLiability::new)
                .collect(Collectors.toList());
        logger.fine("JsonAdaptedPerson successfully created for " + source);
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (notes == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Notes.class.getSimpleName()));
        }
        final Notes modelNotes = new Notes(notes);

        final Set<Policy> modelPolicies = new HashSet<>();
        final Set<Asset> modelAssets = new HashSet<>();
        final Set<Liability> modelLiabilities = new HashSet<>();

        if (policies == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "policies"));
        }
        for (JsonAdaptedPolicy policy : policies) {
            modelPolicies.add(policy.toModelType());
        }
        if (policies.size() != modelPolicies.size()) {
            throw new IllegalValueException(String.format(DUPLICATE_ATTRIBUTE_IN_LIST, "policies"));
        }

        if (assets == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "assets"));
        }
        for (JsonAdaptedAsset asset : assets) {
            modelAssets.add(asset.toModelType());
        }
        if (assets.size() != modelAssets.size()) {
            throw new IllegalValueException(String.format(DUPLICATE_ATTRIBUTE_IN_LIST, "assets"));
        }

        if (liabilities == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "liabilities"));
        }
        for (JsonAdaptedLiability liability : liabilities) {
            modelLiabilities.add(liability.toModelType());
        }
        if (liabilities.size() != modelLiabilities.size()) {
            throw new IllegalValueException(String.format(DUPLICATE_ATTRIBUTE_IN_LIST, "liabilities"));
        }

        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelNotes,
                modelPolicies, modelLiabilities, modelAssets);
    }

}
