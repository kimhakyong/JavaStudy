package net.jackbauer.enumtype;

import net.jackbauer.enumtype.pay.PayGroup;
import net.jackbauer.enumtype.pay.PayType;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

// given, when, then
class PayGroupTest {
    @Test
    public void usePayGroup() {
        // given
        PayType toss = PayType.TOSS;
        PayType kakao_pay = PayType.KAKAO_PAY;

        // when
        PayGroup tossPayGroup = PayGroup.findByPayType(toss);
        PayGroup kakaoPayGroup = PayGroup.findByPayType(kakao_pay);

        // then
        assertThat(tossPayGroup, is(PayGroup.CASH));
        assertThat(kakaoPayGroup, is(PayGroup.CARD));
        assertThat(kakaoPayGroup, not(PayGroup.CASH));

        assertThat(tossPayGroup.name(), is("CASH"));
        assertThat(tossPayGroup.getTitle(), is("현금"));
    }
}