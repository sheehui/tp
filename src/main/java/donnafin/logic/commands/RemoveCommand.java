package donnafin.logic.commands;

import donnafin.commons.core.types.Index;
import donnafin.logic.PersonAdapter;


public class RemoveCommand {

    public static final String MESSAGE_USAGE = ""; //TODO
    private final PersonAdapter personAdapter;
    public RemoveCommand(PersonAdapter personAdapter, PersonAdapter.PersonField field, Index index) {
        this.personAdapter = personAdapter;
    }
}
