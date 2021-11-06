package donnafin.logic.parser;

import static donnafin.testutil.TypicalPersons.getTypicalAddressBook;
import static donnafin.testutil.TypicalPersons.getTypicalPersons;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import donnafin.logic.PersonAdapter;
import donnafin.model.Model;
import donnafin.model.ModelManager;
import donnafin.model.UserPrefs;
import donnafin.model.person.Person;

/**
Used to test the other functions of ParserContext.For test regarding a specific ParserStrategy,
refer to ClientViewParserContextTest or AddressBookParserContextTest
 */
public class ParserContextTest {

    private final AddressBookParser addressBookParser = new AddressBookParser();
    private ContactTabParser contactTabParser;
    private ParserContext parserContext = new ParserContext(addressBookParser);

    @BeforeEach
    public void reset() {
        Person person = getTypicalPersons().get(0);
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), null);
        PersonAdapter personAdapter = new PersonAdapter(model, person);
        contactTabParser = new ContactTabParser(personAdapter);
        parserContext = new ParserContext(addressBookParser);
    }

    @Test
    public void test_constructor() {
        ParserStrategy currentParserStrategy = parserContext.getCurrentParserStrategy();
        assertTrue(strategyIsAddressBookParser());
    }

    @Test
    public void test_setCurrentParserStrategy() {
        parserContext.setCurrentParserStrategy(contactTabParser);
        assertTrue(strategyIsClientParser());
        assertFalse(strategyIsAddressBookParser());

        parserContext.setCurrentParserStrategy(addressBookParser);
        assertTrue(strategyIsAddressBookParser());
        assertFalse(this::strategyIsClientParser);

        //Test if set currentParserStrategyTwice to addressbook has any errors
        parserContext.setCurrentParserStrategy(addressBookParser);
        assertTrue(strategyIsAddressBookParser());
        assertFalse(this::strategyIsClientParser);

        //Test if setting currentParserStrategyTwice has any errors
        parserContext.setCurrentParserStrategy(contactTabParser);
        parserContext.setCurrentParserStrategy(contactTabParser);
        assertTrue(strategyIsClientParser());
        assertFalse(strategyIsAddressBookParser());

    }

    private boolean strategyIsClientParser() {
        return parserContext.getCurrentParserStrategy().equals(contactTabParser);
    }

    private boolean strategyIsAddressBookParser() {
        return parserContext.getCurrentParserStrategy().equals(addressBookParser);
    }
}
