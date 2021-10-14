package donnafin.testutil;

import donnafin.logic.commands.AddCommand;
import donnafin.logic.parser.CliSyntax;
import donnafin.model.person.Person;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(CliSyntax.PREFIX_NAME + person.getName().fullName + " ");
        sb.append(CliSyntax.PREFIX_PHONE + person.getPhone().value + " ");
        sb.append(CliSyntax.PREFIX_EMAIL + person.getEmail().value + " ");
        sb.append(CliSyntax.PREFIX_ADDRESS + person.getAddress().value + " ");
        person.getTags().stream().forEach(
            s -> sb.append(CliSyntax.PREFIX_TAG + s.tagName + " ")
        );

        //Financial info
        person.getPolicies().stream().forEach(
            s -> sb.append(CliSyntax.PREFIX_TAG + s.policyName + " ")
        );
        sb.append(CliSyntax.PREFIX_LIABILITIES + person.getLiabilities().value + " ");
        sb.append(CliSyntax.PREFIX_COMMISSION + person.getCommission().value + " ");
        person.getTags().stream().forEach(
            s -> sb.append(CliSyntax.PREFIX_TAG + s.tagName + " ")
        );
        person.getAssetsSet().stream().forEach(
            s -> sb.append(CliSyntax.PREFIX_TAG + s.assetName + " ")
        );
        return sb.toString();
    }

}
