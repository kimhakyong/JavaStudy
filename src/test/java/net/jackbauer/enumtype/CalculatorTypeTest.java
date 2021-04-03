package net.jackbauer.enumtype;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

// given, when, then
class CalculatorTypeTest {
    @Test
    public void useCalculatorType() {
        // given
        long calcInput = 100L;

        CalculatorType calc_a = CalculatorType.CALC_A;
        CalculatorType calc_b = CalculatorType.CALC_B;
        CalculatorType calc_c = CalculatorType.CALC_C;
        CalculatorType calc_etc = CalculatorType.CALC_ETC;

        // when / then
        assertThat(calc_a.calculate(calcInput), is(100L));
        assertThat(calc_b.calculate(calcInput), is(100L * 10));
        assertThat(calc_c.calculate(calcInput), is(100L * 3));
        assertThat(calc_etc.calculate(calcInput), is(0L));
    }
}