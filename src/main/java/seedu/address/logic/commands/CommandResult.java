package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.function.Consumer;

import seedu.address.ui.Ui;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    private final Consumer<Ui> uiAction;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, Consumer<Ui> uiAction) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.uiAction = uiAction;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, (ui -> { /* do nothing; */ }));
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && uiAction.equals(otherCommandResult.uiAction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, uiAction);
    }

}
