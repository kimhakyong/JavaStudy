package net.jackbauer.enumtype.dbcode;

import org.apache.logging.log4j.core.util.JsonUtils;
import org.junit.jupiter.api.Test;
import org.w3c.dom.ls.LSOutput;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

// given / when / then
class FeeTypeTest {
    @Test
    public void useFeeType() {
        // given / when
        List<EnumMapperValue> collect = Arrays.stream(FeeType.values())
                .map(EnumMapperValue::new)
                .collect(Collectors.toList());

        // then
        EnumMapperValue checkValue1 = new EnumMapperValue("PERCENT", "정율");
        EnumMapperValue checkValue2 = new EnumMapperValue("MONEY", "정액");

        assertTrue(collect.stream().anyMatch(value ->
            value.getCode().equals(checkValue1.getCode()) && value.getTitle().equals(checkValue1.getTitle())
        ));

        assertTrue(collect.stream().anyMatch(value ->
            value.getCode().equals(checkValue2.getCode()) && value.getTitle().equals(checkValue2.getTitle())
        ));

        assertThat(Arrays.toString(FeeType.values()), containsString("PERCENT"));
        assertThat(Arrays.toString(FeeType.values()), containsString("MONEY"));
    }
}