package donnafin.logic.parser;

import static donnafin.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static donnafin.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static donnafin.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static donnafin.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static donnafin.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static donnafin.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static donnafin.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static donnafin.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static donnafin.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static donnafin.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static donnafin.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static donnafin.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static donnafin.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static donnafin.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static donnafin.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static donnafin.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static donnafin.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static donnafin.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static donnafin.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static donnafin.logic.parser.CommandParserTestUtil.assertParseFailure;
import static donnafin.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static donnafin.testutil.TypicalPersons.AMY;

import org.junit.jupiter.api.Test;

import donnafin.logic.commands.AddCommand;
import donnafin.model.person.Address;
import donnafin.model.person.Email;
import donnafin.model.person.Name;
import donnafin.model.person.Person;
import donnafin.model.person.Phone;
import donnafin.testutil.PersonBuilder;

public class AddCommandParserTest {
    private final AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(AMY).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY,
                new AddCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY,
                new AddCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY,
                new AddCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY,
                new AddCommand(expectedPerson));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + ADDRESS_DESC_AMY,
                new AddCommand(expectedPerson));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB,
                Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC,
                Address.MESSAGE_CONSTRAINTS);

        // Insert test for parsing failure for other fields

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC,
                        Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
