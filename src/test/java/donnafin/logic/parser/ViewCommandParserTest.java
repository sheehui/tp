package donnafin.logic.parser;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import donnafin.logic.parser.exceptions.ParseException;

public class ViewCommandParserTest {
    private final ViewCommandParser parser = new ViewCommandParser();

    @Test
    public void parseCommand_emptyNumberFormat_throwsError() {
        assertThrows(ParseException.class, () -> parser.parse(""));
    }

    @Test
    public void parseCommand_positiveInteger_viewCommand() throws ParseException {
        assertNotNull(parser.parse("1"));
        assertNotNull(parser.parse(Integer.MAX_VALUE + ""));
    }

    @Test
    public void parseCommand_zeroOrNegativeNumber_throwsError() {
        assertThrows(ParseException.class, () -> parser.parse("0"));
        assertThrows(ParseException.class, () -> parser.parse("-1"));
        assertThrows(ParseException.class, () -> parser.parse(Integer.MIN_VALUE + ""));
    }

    @Test
    public void parseCommand_numberTooBigOrTooSmall_throwsError() {
        assertThrows(ParseException.class, () -> parser.parse(((long) Integer.MAX_VALUE + 1) + ""));
        assertThrows(ParseException.class, () -> parser.parse(((long) Integer.MIN_VALUE - 1) + ""));
    }

    @Test
    public void parseCommand_invalidCommandFormat_throwsError() {
        assertThrows(ParseException.class, () -> parser.parse("6a"));
    }

}
