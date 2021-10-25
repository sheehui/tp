package donnafin.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import donnafin.commons.core.LogsCenter;
import donnafin.logic.commands.Command;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.ui.UiManager;

public class ParserContext {
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private ParserStrategy currentParserStrategy;

    /**
    Constructor for context
     */
    public ParserContext(ParserStrategy parser) {
        logger.info("Init context");
        this.currentParserStrategy = parser;
    }

    /**
    Changes the currentParserStrategy with a parserStrategy of choice
     */
    public void setCurrentParserStrategy(ParserStrategy parserStrategy) {
        requireNonNull(parserStrategy);
        logger.info("Setting ParserStrategy to " + parserStrategy.getClass().getSimpleName());
        this.currentParserStrategy = parserStrategy;
    }

    /**
    Executes the currentParserStrategy parse command
     */
    public Command executeParserStrategyCommand(String userInput) throws ParseException {
        logger.info("ParserContext executing strategyParserCommand");
        return currentParserStrategy.parseCommand(userInput);
    }

    public ParserStrategy getCurrentParserStrategy() {
        return this.currentParserStrategy;
    }
}
