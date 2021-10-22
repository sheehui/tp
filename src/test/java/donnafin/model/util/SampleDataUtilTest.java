package donnafin.model.util;

import java.util.Objects;

import org.junit.jupiter.api.Test;

import donnafin.model.ReadOnlyAddressBook;

public class SampleDataUtilTest {

    @Test
    public void ensureSampleDataUtilCanInstantiate() {
        ReadOnlyAddressBook addressBook = SampleDataUtil.getSampleAddressBook();
        Objects.requireNonNull(addressBook);
    }
}
