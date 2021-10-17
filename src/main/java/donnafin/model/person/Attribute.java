package donnafin.model.person;

/**
 * The fields in the person class should implement these attributes
 */
public abstract class Attribute {

    public static String DELIMITER = "^]";
    public abstract String toString();
    public abstract boolean equals(Object other);
    public abstract int hashCode();

}
