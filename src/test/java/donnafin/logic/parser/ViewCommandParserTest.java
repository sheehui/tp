package donnafin.logic.parser;

import donnafin.logic.commands.ViewCommand;
import donnafin.logic.parser.exceptions.ParseException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ViewCommandParserTest {
    private final ViewCommandParser parser = new ViewCommandParser();
    
    @Test
    public void parser_invalidCommandFormat_throwsError() {
        assertThrows(ParseException.class, () -> parser.parse("view"));
    }
    
}
