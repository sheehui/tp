package donnafin.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import donnafin.commons.core.LogsCenter;
import donnafin.logic.commands.Command;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.ui.UiManager;

public class Context {
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private ParserStrategy currentStrategyParser;

    /**
    Constructor for context
     */
    public Context(ParserStrategy parser) {
        logger.info("Init context");
        this.currentStrategyParser = parser;
    }

    /**
    Changes the currentParserStrategy with a parserStrategy of choice
     */
    public void setCurrentParserStrategy(ParserStrategy parserStrategy) {
        requireNonNull(parserStrategy);
        logger.info("Setting ParserStrategy");
        this.currentStrategyParser = parserStrategy;
    }

    /**
    Executes the currentParserStrategy parse command
     */
    public Command executeParserStrategyCommand(String userInput) throws ParseException {
        logger.info("Context executing strategyParserCommand");
        return currentStrategyParser.parseCommand(userInput);
    }

    public ParserStrategy getCurrentStrategyParser() {
        return this.currentStrategyParser;
    }
}
