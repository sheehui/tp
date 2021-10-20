package donnafin.logic.parser;

import static donnafin.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static donnafin.testutil.Assert.assertThrows;
import static donnafin.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import donnafin.commons.core.types.Money;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.model.person.Address;
import donnafin.model.person.Email;
import donnafin.model.person.Name;
import donnafin.model.person.Phone;
import donnafin.model.tag.Tag;
import donnafin.ui.Ui;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseMoney_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseMoney(null));
    }

    @Test
    public void parseMoney_validWithDollarSign() throws ParseException {
        assertEquals(new Money(100), ParserUtil.parseMoney("$1"));
        assertEquals(new Money(100000), ParserUtil.parseMoney("$1000"));
    }

    @Test
    public void parseMoney_validWithDollarSignCents() throws ParseException {
        assertEquals(new Money(105), ParserUtil.parseMoney("$1.05"));
    }

    @Test
    public void parseMoney_invalidWithoutDollarSign() throws ParseException {
        assertThrows(ParseException.class, () -> ParserUtil.parseMoney("1"));
        assertThrows(ParseException.class, () -> ParserUtil.parseMoney("1.05"));
    }

    @Test
    public void parseMoney_validWithWhiteSpace() throws ParseException {
        // trailing whitespace
        assertEquals(new Money(100), ParserUtil.parseMoney("  $1  "));
        assertEquals(new Money(105), ParserUtil.parseMoney("  $1.05  "));

        // whitespace after dollar sign
        assertEquals(new Money(100), ParserUtil.parseMoney("  $  1"));
        assertEquals(new Money(105), ParserUtil.parseMoney("  $    1.05"));
    }

    @Test
    public void parseMoney_validWithNegative() throws ParseException {
        // trailing whitespace
        assertEquals(new Money(-100), ParserUtil.parseMoney("-$1"));
        assertEquals(new Money(-105), ParserUtil.parseMoney("-$1.05"));

        // whitespace after dollar sign
        assertEquals(new Money(-100), ParserUtil.parseMoney("-$1"));
        assertEquals(new Money(-105), ParserUtil.parseMoney("-$1.05"));
    }

    @Test
    public void parseMoney_validWithNegativeAndWhiteSpace() throws ParseException {
        // trailing whitespace
        assertEquals(new Money(-100), ParserUtil.parseMoney(" - $1  "));
        assertEquals(new Money(-105), ParserUtil.parseMoney(" - $1.05  "));

        // whitespace after dollar sign
        assertEquals(new Money(-100), ParserUtil.parseMoney("  -  $  1"));
        assertEquals(new Money(-105), ParserUtil.parseMoney(" -  $    1.05"));
    }

    @Test
    public void parseMoney_invalidFormats() throws ParseException {
        assertThrows(ParseException.class, () -> ParserUtil.parseMoney("1.00$"));
        assertThrows(ParseException.class, () -> ParserUtil.parseMoney("1.0.0"));
        assertThrows(ParseException.class, () -> ParserUtil.parseMoney(".50"));
        assertThrows(ParseException.class, () -> ParserUtil.parseMoney("abc $2.50"));
        assertThrows(ParseException.class, () -> ParserUtil.parseMoney("$2.50 abc"));
    }

    @Test
    public void parseMoney_invalidWithWrongPrecision() throws ParseException {
        assertThrows(ParseException.class, () -> ParserUtil.parseMoney("1.0"));
        assertThrows(ParseException.class, () -> ParserUtil.parseMoney("1.000"));
    }

    @Test
    public void parseTab_withUpperLowerOrTitleCase_success() throws ParseException {
        assertEquals(Ui.ClientViewTab.Contact, ParserUtil.parseTab("contact"));
        assertEquals(Ui.ClientViewTab.Policies, ParserUtil.parseTab("policies"));
        assertEquals(Ui.ClientViewTab.Assets, ParserUtil.parseTab("assets"));
        assertEquals(Ui.ClientViewTab.Liabilities, ParserUtil.parseTab("liabilities"));
        assertEquals(Ui.ClientViewTab.Notes, ParserUtil.parseTab("notes"));

        assertEquals(Ui.ClientViewTab.Contact, ParserUtil.parseTab("CONTACT"));
        assertEquals(Ui.ClientViewTab.Policies, ParserUtil.parseTab("POLICIES"));
        assertEquals(Ui.ClientViewTab.Assets, ParserUtil.parseTab("ASSETS"));
        assertEquals(Ui.ClientViewTab.Liabilities, ParserUtil.parseTab("LIABILITIES"));
        assertEquals(Ui.ClientViewTab.Notes, ParserUtil.parseTab("NOTES"));

        assertEquals(Ui.ClientViewTab.Contact, ParserUtil.parseTab("Contact"));
        assertEquals(Ui.ClientViewTab.Policies, ParserUtil.parseTab("Policies"));
        assertEquals(Ui.ClientViewTab.Assets, ParserUtil.parseTab("Assets"));
        assertEquals(Ui.ClientViewTab.Liabilities, ParserUtil.parseTab("Liabilities"));
        assertEquals(Ui.ClientViewTab.Notes, ParserUtil.parseTab("Notes"));
    }


    @Test
    public void parseTab_unexpectedPluralSingular_success() throws ParseException {
        assertEquals(Ui.ClientViewTab.Contact, ParserUtil.parseTab("Contacts"));
        assertEquals(Ui.ClientViewTab.Policies, ParserUtil.parseTab("Policy"));
        assertEquals(Ui.ClientViewTab.Assets, ParserUtil.parseTab("Asset"));
        assertEquals(Ui.ClientViewTab.Liabilities, ParserUtil.parseTab("Liability"));
        assertEquals(Ui.ClientViewTab.Notes, ParserUtil.parseTab("Note"));
    }


    @Test
    public void parseTab_withInitialsOnly_success() throws ParseException {
        assertEquals(Ui.ClientViewTab.Contact, ParserUtil.parseTab("c"));
        assertEquals(Ui.ClientViewTab.Policies, ParserUtil.parseTab("p"));
        assertEquals(Ui.ClientViewTab.Assets, ParserUtil.parseTab("a"));
        assertEquals(Ui.ClientViewTab.Liabilities, ParserUtil.parseTab("l"));
        assertEquals(Ui.ClientViewTab.Notes, ParserUtil.parseTab("n"));

        assertEquals(Ui.ClientViewTab.Contact, ParserUtil.parseTab("C"));
        assertEquals(Ui.ClientViewTab.Policies, ParserUtil.parseTab("P"));
        assertEquals(Ui.ClientViewTab.Assets, ParserUtil.parseTab("A"));
        assertEquals(Ui.ClientViewTab.Liabilities, ParserUtil.parseTab("L"));
        assertEquals(Ui.ClientViewTab.Notes, ParserUtil.parseTab("N"));
    }

    @Test
    public void parseTab_badSpellingOrInvalidTab_throwsParseErr() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTab("x"));
        assertThrows(ParseException.class, () -> ParserUtil.parseTab("j"));
        assertThrows(ParseException.class, () -> ParserUtil.parseTab("k"));
        assertThrows(ParseException.class, () -> ParserUtil.parseTab(""));
        assertThrows(ParseException.class, () -> ParserUtil.parseTab("abcdef"));
        assertThrows(ParseException.class, () -> ParserUtil.parseTab("coontact"));
        assertThrows(ParseException.class, () -> ParserUtil.parseTab("orsets"));
    }

    @Test
    public void parseTab_multipleTabs_throwError() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTab("contact policy"));
        assertThrows(ParseException.class, () -> ParserUtil.parseTab("c p"));
    }
}
