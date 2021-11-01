package donnafin.model.person;

/**
 * The fields in the person class should implement these attributes
 */
public interface Attribute {

    String toString();
    boolean equals(Object other);
    int hashCode();
    boolean isPossibleDuplicate(Attribute other);
}
