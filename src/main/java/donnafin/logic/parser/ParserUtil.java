package donnafin.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import donnafin.commons.core.types.Index;
import donnafin.commons.util.StringUtil;
import donnafin.logic.parser.exceptions.ParseException;
import donnafin.model.person.Address;
import donnafin.model.person.Asset;
import donnafin.model.person.Email;
import donnafin.model.person.Liability;
import donnafin.model.person.Name;
import donnafin.model.person.Phone;
import donnafin.model.person.Policy;
import donnafin.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    private static final String ATTRIBUTE_DELIMITER = ";;;";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String policy} into a {@code Policy}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code policy} is invalid.
     */
    public static Policy parsePolicy(String policy) throws ParseException {
        String[] details = policy.split(ATTRIBUTE_DELIMITER);
        Arrays.stream(details).map(Objects::requireNonNull);

        String trimmedPolicy = policy.trim();
        if (!Policy.isValidPolicy(trimmedPolicy) || details.length != 5) {
            throw new ParseException(Policy.MESSAGE_CONSTRAINTS);
        }

        return new Policy(details);
    }

    /**
     * Parses {@code Collection<String> policy} into a {@code Set<Policy>}.
     */
    public static Set<Policy> parsePolicies(Collection<String> policies) throws ParseException {
        requireNonNull(policies);
        final Set<Policy> policySet = new HashSet<>();

        for (String policyName : policies) {
            policySet.add(parsePolicy(policyName));
        }

        return policySet;
    }

    /**
     * Parses a {@code String asset} into a {@code Asset}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code asset} is invalid.
     */
    public static Asset parseAsset(String asset) throws ParseException {
        String[] details = asset.split(ATTRIBUTE_DELIMITER);
        Arrays.stream(details).map(Objects::requireNonNull);

        String trimmedAsset = asset.trim();
        if (!Asset.isValidAsset(trimmedAsset) || details.length != 4) {
            throw new ParseException(Asset.MESSAGE_CONSTRAINTS);
        }
        return new Asset(details);
    }

    /**
     * Parses {@code Collection<String> assets} into a {@code Set<Asset>}.
     */
    public static Set<Asset> parseAssets(Collection<String> assets) throws ParseException {
        requireNonNull(assets);
        final Set<Asset> assetSet = new HashSet<>();

        for (String assetName : assets) {
            assetSet.add(parseAsset(assetName));
        }

        return assetSet;
    }


    /**
     * Parses a {@code String liability} into a {@code Liability}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code liability} is invalid.
     */
    public static Liability parseLiability(String liability) throws ParseException {
        String[] details = liability.split(ATTRIBUTE_DELIMITER);
        Arrays.stream(details).map(Objects::requireNonNull);

        String trimmedLiability = liability.trim();
        if (!Liability.isValidLiability(trimmedLiability) || details.length != 4) {
            throw new ParseException(Liability.MESSAGE_CONSTRAINTS);
        }

        return new Liability(details);
    }

    /**
     * Parses {@code Collection<String> liabilities} into a {@code Set<Liability>}.
     */
    public static Set<Liability> parseLiabilities(Collection<String> liabilities) throws ParseException {
        requireNonNull(liabilities);
        final Set<Liability> liabilitySet = new HashSet<>();

        for (String liabilityName : liabilities) {
            liabilitySet.add(parseLiability(liabilityName));
        }

        return liabilitySet;
    }

}
