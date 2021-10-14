package donnafin.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import donnafin.commons.exceptions.IllegalValueException;
import donnafin.model.person.Assets;

/**
 * Jackson-friendly version of {@link Assets}.
 */
class JsonAdaptedAssets {

    private final String assetsName;

    /**
     * Constructs a {@code JsonAdaptedAssets} with the given {@code assetsName}.
     */
    @JsonCreator
    public JsonAdaptedAssets(String assetsName) {
        this.assetsName = assetsName;
    }

    /**
     * Converts a given {@code Assets} into this class for Jackson use.
     */
    public JsonAdaptedAssets(Assets source) {
        assetsName = source.assetName;
    }

    @JsonValue
    public String getAssetsName() {
        return assetsName;
    }

    /**
     * Converts this Jackson-friendly adapted assets object into the model's {@code Assets} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted assets.
     */
    public Assets toModelType() throws IllegalValueException {
        if (!Assets.isValidAsset(assetsName)) {
            throw new IllegalValueException(Assets.MESSAGE_CONSTRAINTS);
        }
        return new Assets(assetsName);
    }
}
