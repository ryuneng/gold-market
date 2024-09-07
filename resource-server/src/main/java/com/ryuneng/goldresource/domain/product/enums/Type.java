package com.ryuneng.goldresource.domain.product.enums;

import lombok.Getter;

@Getter
public enum Type {

    PURCHASE("구매"),
    SALE("판매");

    private final String description;

    Type(String description) {

        this.description = description;
    }
}
