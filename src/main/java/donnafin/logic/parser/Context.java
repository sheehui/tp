package donnafin.logic.parser;

import donnafin.commons.core.LogsCenter;
import donnafin.logic.commands.Command;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.ui.UiManager;

import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

public class Context {
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private ParserStrategy currentStrategyParser;

    public Context(ParserStrategy parser) {
        logger.info("Init context");
        this.currentStrategyParser = parser;
    }

    public void setCurrentParserStrategy(ParserStrategy parserStrategy) {
        requireNonNull(parserStrategy);
        logger.info("Setting ParserStrategy");
        this.currentStrategyParser = parserStrategy;
    }

    public Command executeParserStrategyCommand(String userInput) throws ParseException {
        logger.info("Context executing strategyParserCommand");
        return currentStrategyParser.parseCommand(userInput);
    }

    public ParserStrategy getCurrentStrategyParser() {
        return this.currentStrategyParser;
    }
}
