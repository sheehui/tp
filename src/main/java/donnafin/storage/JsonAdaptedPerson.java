package donnafin.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import donnafin.commons.exceptions.IllegalValueException;
import donnafin.model.person.Address;
import donnafin.model.person.Assets;
import donnafin.model.person.Commission;
import donnafin.model.person.Email;
import donnafin.model.person.Liabilities;
import donnafin.model.person.Name;
import donnafin.model.person.Notes;
import donnafin.model.person.Person;
import donnafin.model.person.Phone;
import donnafin.model.person.Policy;
import donnafin.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String notes;
    private final String liabilities;
    private final String commission;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final List<JsonAdaptedPolicy> policies = new ArrayList<>();
    private final List<JsonAdaptedAssets> assets = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(
            @JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("tagged") List<JsonAdaptedTag> tagged, @JsonProperty("notes") String notes,
            @JsonProperty("policies") List<JsonAdaptedPolicy> policies,
            @JsonProperty("liabilities") String liabilities,
            @JsonProperty("commission") String commission,
            @JsonProperty("assets") List<JsonAdaptedAssets> assets) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.notes = notes == null ? "" : notes;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        if (policies != null) {
            this.policies.addAll(policies);
        }
        this.liabilities = liabilities;
        this.commission = commission;
        if (assets != null) {
            this.assets.addAll(assets);
        }
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
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        policies.addAll(source.getPolicies().stream()
                .map(JsonAdaptedPolicy::new)
                .collect(Collectors.toList()));
        liabilities = source.getLiabilities().value;
        commission = source.getCommission().value;
        assets.addAll(source.getAssetsSet().stream()
                .map(JsonAdaptedAssets::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        final List<Policy> personPolicies = new ArrayList<>();
        final List<Assets> personAssets = new ArrayList<>();

        for (JsonAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        for (JsonAdaptedPolicy policy : policies) {
            personPolicies.add(policy.toModelType());
        }
        for (JsonAdaptedAssets asset : assets) {
            personAssets.add(asset.toModelType());
        }


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

        if (liabilities == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Liabilities.class.getSimpleName()));
        }
        if (!Liabilities.isValidLiability(liabilities)) {
            throw new IllegalValueException(Liabilities.MESSAGE_CONSTRAINTS);
        }
        final Liabilities modelLiability = new Liabilities(liabilities);

        if (commission == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Commission.class.getSimpleName()));
        }
        if (!Commission.isValidCommissionName(commission)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Commission modelCommission = new Commission(commission);

        final Set<Tag> modelTags = new HashSet<>(personTags);
        final Notes modelNotes = new Notes(notes);
        final Set<Policy> modelPolicy = new HashSet<>(personPolicies);
        final Set<Assets> modelAssets = new HashSet<>(personAssets);
        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelTags, modelNotes,
                modelPolicy, modelLiability, modelCommission, modelAssets);
        );
    }

}
