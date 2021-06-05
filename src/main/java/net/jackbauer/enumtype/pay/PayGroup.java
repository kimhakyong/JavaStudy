package net.jackbauer.enumtype.pay;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * https://woowabros.github.io/tools/2017/07/10/java-enum-uses.html
 */
@Getter
public enum PayGroup {
    CASH("현금", Arrays.asList(PayType.ACCOUNT_TRANSFER,
            PayType.REMITTANCE, PayType.ON_SITE_PAYMENT, PayType.TOSS)),
    CARD("카드", Arrays.asList(PayType.PAYCO, PayType.CARD,
            PayType.KAKAO_PAY, PayType.BAEMIN_PAY)),
    ETC("기타", Arrays.asList(PayType.POINT, PayType.COUPON)),
    EMPTY("없음", Collections.EMPTY_LIST);

    private final String title;
    private final List<PayType> payList;

    PayGroup(String title, List<PayType> payList) {
        this.title = title;
        this.payList = payList;
    }

    public static PayGroup findByPayType(PayType payType) {
        return Arrays.stream(PayGroup.values())
                .filter(payGroup -> payGroup.hasPayCode(payType))
                .findAny()
                .orElse(EMPTY);
    }

    public boolean hasPayCode(PayType payType) {
        return payList.stream()
                .anyMatch(pay -> pay == payType);
    }
}
