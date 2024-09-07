package com.ryuneng.goldresource.domain.order.enums;

import lombok.Getter;

@Getter
public enum Status {

    ORDER_PLACED("주문완료"),
    PAID("입금완료"),
    SHIPPED("발송완료"),
    RECEIVED("수령완료"),
    TRANSFER_COMPLETED("송금완료");

    private final String description;

    Status(String description) {

        this.description = description;
    }
}
