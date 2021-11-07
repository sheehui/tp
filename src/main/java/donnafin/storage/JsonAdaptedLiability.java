//@@author sheehui
package donnafin.storage;

import static donnafin.commons.util.CollectionUtil.requireAllNonNull;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import donnafin.commons.core.LogsCenter;
import donnafin.commons.exceptions.IllegalValueException;
import donnafin.model.person.Liability;

public class JsonAdaptedLiability {

    private static final Logger logger = LogsCenter.getLogger(JsonAdaptedLiability.class);
    private final String liabilityName;
    private final String liabilityType;
    private final String liabilityValue;
    private final String liabilityRemarks;

    /**
     * Constructs a {@code JsonAdaptedLiability} with the given {@code liabilityName}.
     */
    @JsonCreator
    public JsonAdaptedLiability(@JsonProperty("name") String liabilityName, @JsonProperty("type") String liabilityType,
                                @JsonProperty("value") String liabilityValue,
                                @JsonProperty("remarks") String liabilityRemarks) {
        this.liabilityName = liabilityName;
        this.liabilityRemarks = liabilityRemarks;
        this.liabilityValue = liabilityValue;
        this.liabilityType = liabilityType;
    }

    /**
     * Converts a given {@code Liability} into this class for Jackson use.
     */
    public JsonAdaptedLiability(Liability source) {
        liabilityName = source.getName();
        liabilityType = source.getType();
        liabilityValue = source.getValue().toString();
        liabilityRemarks = source.getRemarks();
        logger.fine("JsonAdaptedLiability successfully created for " + source);
    }

    @JsonProperty("name")
    public String getLiabilityName() {
        return liabilityName;
    }

    @JsonProperty("type")
    public String getLiabilityType() {
        return liabilityType;
    }

    @JsonProperty("value")
    public String getLiabilityValue() {
        return liabilityValue;
    }

    @JsonProperty("remarks")
    public String getLiabilityRemarks() {
        return liabilityRemarks;
    }

    /**
     * Converts this Jackson-friendly adapted assets object into the model's {@code Liability} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted liability.
     */
    public Liability toModelType() throws IllegalValueException {
        try {
            requireAllNonNull(liabilityName, liabilityType, liabilityValue, liabilityRemarks);
            return new Liability(liabilityName, liabilityType, liabilityValue, liabilityRemarks);
        } catch (NullPointerException e) {
            throw new IllegalValueException("Null found value in an object in 'liabilities' field.");
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(e.getMessage());
        }
    }
}
