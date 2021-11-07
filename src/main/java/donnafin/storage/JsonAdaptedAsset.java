//@@author sheehui
package donnafin.storage;

import static donnafin.commons.util.CollectionUtil.requireAllNonNull;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import donnafin.commons.core.LogsCenter;
import donnafin.commons.exceptions.IllegalValueException;
import donnafin.model.person.Asset;

/**
 * Jackson-friendly version of {@link Asset}.
 */
class JsonAdaptedAsset {

    private static final Logger logger = LogsCenter.getLogger(JsonAdaptedAsset.class);
    private final String assetName;
    private final String assetType;
    private final String assetValue;
    private final String assetRemarks;

    /**
     * Constructs a {@code JsonAdaptedAsset} with the given {@code assetName}.
     */
    @JsonCreator
    public JsonAdaptedAsset(@JsonProperty("name") String assetName, @JsonProperty("type") String assetType,
                            @JsonProperty("value") String assetValue, @JsonProperty("remarks") String assetRemarks) {
        this.assetName = assetName;
        this.assetRemarks = assetRemarks;
        this.assetType = assetType;
        this.assetValue = assetValue;
    }

    /**
     * Converts a given {@code Asset} into this class for Jackson use.
     */
    public JsonAdaptedAsset(Asset source) {
        assetName = source.getName();
        assetValue = source.getValue().toString();
        assetType = source.getType();
        assetRemarks = source.getRemarks();
        logger.fine("JsonAdaptedAsset successfully created for " + source);
    }

    @JsonProperty("name")
    public String getAssetName() {
        return assetName;
    }

    @JsonProperty("type")
    public String getAssetType() {
        return assetType;
    }

    @JsonProperty("value")
    public String getAssetValue() {
        return assetValue;
    }

    @JsonProperty("remarks")
    public String getAssetRemarks() {
        return assetRemarks;
    }

    /**
     * Converts this Jackson-friendly adapted assets object into the model's {@code Asset} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted assets.
     */
    public Asset toModelType() throws IllegalValueException {
        try {
            requireAllNonNull(assetName, assetType, assetValue, assetRemarks);
            return new Asset(assetName, assetType, assetValue, assetRemarks);
        } catch (NullPointerException ignored) {
            throw new IllegalValueException("Null found value in an object in 'assets' field.");
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(e.getMessage());
        }
    }

}
