package donnafin.logic.parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
Used to test the other functions of Context.For test regarding a specific ParserStrategy,
refer to ContextClientParserTest or ContextAddressBookParserTest
 */
public class ContextTest {

    private final AddressBookParser addressBookParser = new AddressBookParser();
    private final ClientParser clientParser = new ClientParser();
    private Context context = new Context(addressBookParser);

    @BeforeEach
    public void reset() {
        context = new Context(addressBookParser);
    }

    @Test
    public void test_constructor() {
        ParserStrategy currentParserStrategy = context.getCurrentStrategyParser();
        assertTrue(strategyIsAddressBookParser());
    }

    @Test
    public void test_setCurrentParserStrategy() {
        context.setCurrentParserStrategy(clientParser);
        assertTrue(strategyIsClientParser());
        assertFalse(strategyIsAddressBookParser());

        context.setCurrentParserStrategy(addressBookParser);
        assertTrue(strategyIsAddressBookParser());
        assertFalse(this::strategyIsClientParser);

        //Test if set currentParserStrategyTwice to addressbook has any errors
        context.setCurrentParserStrategy(addressBookParser);
        assertTrue(strategyIsAddressBookParser());
        assertFalse(this::strategyIsClientParser);

        //Test if setting currentParserStrategyTwice has any errors
        context.setCurrentParserStrategy(clientParser);
        context.setCurrentParserStrategy(clientParser);
        assertTrue(strategyIsClientParser());
        assertFalse(strategyIsAddressBookParser());

    }

    private boolean strategyIsClientParser() {
        return context.getCurrentStrategyParser().equals(clientParser);
    }

    private boolean strategyIsAddressBookParser() {
        return context.getCurrentStrategyParser().equals(addressBookParser);
    }
}
