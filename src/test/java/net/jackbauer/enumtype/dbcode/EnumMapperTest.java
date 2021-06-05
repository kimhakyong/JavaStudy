package net.jackbauer.enumtype.dbcode;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// given / when / then
class EnumMapperTest {
    @Test
    public void useEnumMapper() {
        // given
        EnumMapper enumMapper = new EnumMapper();
        enumMapper.put("FeeType", FeeType.class);

        // when
        List<EnumMapperValue> feeType = enumMapper.get("FeeType");
        assertTrue(feeType.stream().anyMatch(value ->
                value.getCode().equals("PERCENT") && value.getTitle().equals("정율")
        ));
        assertTrue(feeType.stream().anyMatch(value ->
                value.getCode().equals("MONEY") && value.getTitle().equals("정액")
        ));
    }
}