package donnafin.logic.parser;

import donnafin.commons.core.LogsCenter;
import donnafin.logic.commands.Command;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.ui.UiManager;

import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

public class Context {
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private StrategyParser currentStrategyParser;

    public Context(StrategyParser parser) {
        logger.info("Init context");
        this.currentStrategyParser = parser;
    }

    public void setCurrentStrategyParser(StrategyParser strategyParser) {
        requireNonNull(strategyParser);
        logger.info("Setting strategy parser");
        this.currentStrategyParser = strategyParser;
    }

    public Command executeStrategyParseCommand(String userInput) throws ParseException {
        logger.info("Context executing strategyParserCommand");
        return currentStrategyParser.parseCommand(userInput);
    }
}
