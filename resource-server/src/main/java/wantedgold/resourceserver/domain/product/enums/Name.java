package wantedgold.resourceserver.domain.product.enums;

import lombok.Getter;

@Getter
public enum Name {

    GOLD_99_9("99.9% 금"),
    GOLD_99_99("99.99% 금");

    private final String description;

    Name(String description) {

        this.description = description;
    }

}
