package donnafin.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import donnafin.commons.exceptions.IllegalValueException;
import donnafin.model.person.Liability;

public class JsonAdaptedLiability {
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
        liabilityName = source.name;
        liabilityType = source.type;
        liabilityValue = source.value.toString();
        liabilityRemarks = source.remarks;
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
        return new Liability(liabilityName, liabilityType, liabilityValue, liabilityRemarks);
    }
}
