package donnafin.model.person;

/**
 * The fields in the person class should implement these attributes
 */
public interface Attribute {

    public String toString();
    public boolean equals(Object other);
    public int hashCode();

}
