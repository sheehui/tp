package donnafin.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import donnafin.commons.exceptions.IllegalValueException;
import donnafin.model.person.Asset;

/**
 * Jackson-friendly version of {@link Asset}.
 */
class JsonAdaptedAsset {

    private final String assetsName;

    /**
     * Constructs a {@code JsonAdaptedAsset} with the given {@code assetsName}.
     */
    @JsonCreator
    public JsonAdaptedAsset(String assetsName) {
        this.assetsName = assetsName;
    }

    /**
     * Converts a given {@code Asset} into this class for Jackson use.
     */
    public JsonAdaptedAsset(Asset source) {
        assetsName = source.name;
    }

    @JsonValue
    public String getAssetName() {
        return assetsName;
    }

    /**
     * Converts this Jackson-friendly adapted assets object into the model's {@code Asset} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted assets.
     */
    public Asset toModelType() throws IllegalValueException {
        if (!Asset.isValidAsset(assetsName)) {
            throw new IllegalValueException(Asset.MESSAGE_CONSTRAINTS);
        }
        return new Asset(assetsName);
    }
}
