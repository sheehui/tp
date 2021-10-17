package donnafin.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import donnafin.commons.exceptions.IllegalValueException;
import donnafin.model.person.Asset;
import donnafin.model.person.Liability;

public class JsonAdaptedLiability {
    private final String liabilityName;

    /**
     * Constructs a {@code JsonAdaptedLiability} with the given {@code liabilityName}.
     */
    @JsonCreator
    public JsonAdaptedLiability(String liabilityName) {
        this.liabilityName = liabilityName;
    }

    /**
     * Converts a given {@code Asset} into this class for Jackson use.
     */
    public JsonAdaptedLiability(Liability source) {
        liabilityName = source.name;
    }

    @JsonValue
    public String getLiabilityName() {
        return liabilityName;
    }

    /**
     * Converts this Jackson-friendly adapted assets object into the model's {@code Asset} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted assets.
     */
    public Liability toModelType() throws IllegalValueException {
        if (!Liability.isValidLiability(liabilityName)) {
            throw new IllegalValueException(Asset.MESSAGE_CONSTRAINTS);
        }
        return new Liability(liabilityName);
    }
}
