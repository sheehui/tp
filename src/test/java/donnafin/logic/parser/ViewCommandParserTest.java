package donnafin.logic.parser;

import donnafin.logic.commands.ViewCommand;
import donnafin.logic.parser.exceptions.ParseException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ViewCommandParserTest {
    private final ViewCommandParser parser = new ViewCommandParser();
    
    @Test
    public void parseCommand_emptyNumberFormat_throwsError() {
        assertThrows(ParseException.class, () -> parser.parse(""));
    }
    
    @Test
    public void parseCommand_returns_viewCommand() throws ParseException{
        assertTrue(parser.parse("1") instanceof ViewCommand);
    }
    
    @Test 
    public void parseCommand_numberFormatException_throwsError() {
        assertThrows(ParseException.class, () -> parser.parse("0"));
    }
    
    @Test
    public void parseCommand_invalidCommandFormat_throwsError() {
        assertThrows(ParseException.class, () -> parser.parse("6a"));
    }
    
}
