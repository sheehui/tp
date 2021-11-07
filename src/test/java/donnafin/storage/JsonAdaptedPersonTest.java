package donnafin.storage;

import static donnafin.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static donnafin.testutil.Assert.assertThrows;
import static donnafin.testutil.TypicalPersons.BENSON;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import donnafin.commons.exceptions.IllegalValueException;
import donnafin.model.person.Address;
import donnafin.model.person.Asset;
import donnafin.model.person.Email;
import donnafin.model.person.Liability;
import donnafin.model.person.Name;
import donnafin.model.person.Phone;
import donnafin.model.person.Policy;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final List<JsonAdaptedPolicy> INVALID_POLICIES = List.of(
            new JsonAdaptedPolicy("Everlong", "Foo", "Fighters", "$3", "$20")
    );
    private static final List<JsonAdaptedAsset> INVALID_ASSETS = List.of(
            new JsonAdaptedAsset("Bender", "Robot", "30", "Bends pipes")
    );
    private static final List<JsonAdaptedLiability> INVALID_LIABILITIES = List.of(
            new JsonAdaptedLiability("Philip Fry", "Human", "incalculable", "Pizza boy")
    );
    private static final List<JsonAdaptedPolicy> POLICIES_WITH_NULL_FIELD = List.of(
            new JsonAdaptedPolicy("Everlong", "Foo", null, "$3", "$20")
    );
    private static final List<JsonAdaptedAsset> ASSETS_WITH_NULL_FIELD = List.of(
            new JsonAdaptedAsset("Bender", "Robot", null, "Bends pipes")
    );
    private static final List<JsonAdaptedLiability> LIABILITIES_WITH_NULL_FIELD = List.of(
            new JsonAdaptedLiability("Bender", "Robot", null, "Bends pipes")
    );

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_NOTES = BENSON.getNotes().toString();

    //Financial Information
    private static final List<JsonAdaptedPolicy> VALID_POLICY = BENSON.getPolicies().stream()
            .map(JsonAdaptedPolicy::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedLiability> VALID_LIABILITIES = BENSON.getLiabilities().stream()
            .map(JsonAdaptedLiability::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedAsset> VALID_ASSETS = BENSON.getAssets().stream()
            .map(JsonAdaptedAsset::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_NOTES,
                        VALID_POLICY, VALID_LIABILITIES, VALID_ASSETS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_NOTES, VALID_POLICY, VALID_LIABILITIES, VALID_ASSETS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_NOTES,
                        VALID_POLICY, VALID_LIABILITIES, VALID_ASSETS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_NOTES, VALID_POLICY, VALID_LIABILITIES, VALID_ASSETS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPolicy_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_NOTES, INVALID_POLICIES, VALID_LIABILITIES, VALID_ASSETS);
        String expectedMessage = Policy.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPolicies_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_NOTES, null, VALID_LIABILITIES, VALID_ASSETS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "policies");
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullFieldPolicy_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_NOTES, POLICIES_WITH_NULL_FIELD, VALID_LIABILITIES, VALID_ASSETS);
        String expectedMessage = "Null found value in an object in 'policies' field.";
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }


    @Test
    public void toModelType_invalidAsset_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_NOTES, VALID_POLICY, VALID_LIABILITIES, INVALID_ASSETS);
        String expectedMessage = Asset.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAsset_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_NOTES, VALID_POLICY, VALID_LIABILITIES, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "assets");
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullFieldAsset_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_NOTES, VALID_POLICY, VALID_LIABILITIES, ASSETS_WITH_NULL_FIELD);
        String expectedMessage = "Null found value in an object in 'assets' field.";
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidLiability_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_NOTES, VALID_POLICY, INVALID_LIABILITIES, VALID_ASSETS);
        String expectedMessage = Liability.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullLiability_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_NOTES, VALID_POLICY, null, VALID_ASSETS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "liabilities");
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullFieldLiability_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_NOTES, VALID_POLICY, LIABILITIES_WITH_NULL_FIELD, VALID_ASSETS);
        String expectedMessage = "Null found value in an object in 'liabilities' field.";
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_NOTES,
                        VALID_POLICY, VALID_LIABILITIES, VALID_ASSETS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                VALID_NOTES, VALID_POLICY, VALID_LIABILITIES, VALID_ASSETS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS,
                        VALID_NOTES, VALID_POLICY, VALID_LIABILITIES, VALID_ASSETS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_NOTES, VALID_POLICY, VALID_LIABILITIES, VALID_ASSETS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

}
